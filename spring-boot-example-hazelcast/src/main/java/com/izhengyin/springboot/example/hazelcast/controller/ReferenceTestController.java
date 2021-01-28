package com.izhengyin.springboot.example.hazelcast.controller;

import com.hazelcast.collection.IList;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-28 16:47
 */
@RestController
public class ReferenceTestController {
    private final HazelcastInstance hazelcastInstance;
    private final IMap<String,Student> studentIMap;

    public ReferenceTestController(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
        this.studentIMap = this.hazelcastInstance.getMap("studentIMap");

    }

    @PostMapping("/student/{name}")
    public Student createStudent(@PathVariable String name){
        Student student = new Student();
        student.setName(name);
        List<String> cousers = student.getCourses(hazelcastInstance);
        cousers.addAll(Stream.of(1,2,3).map(i -> "couser-"+i).collect(Collectors.toList()));
        return  studentIMap.put(name,student);
    }

    @PostMapping("/mockConcurrentUpdate/{name}/course/{course}")
    public Student mockConcurrentAddCourse(@PathVariable String name, @PathVariable String course) throws InterruptedException{
        Student student = studentIMap.get(name);
        if(Objects.isNull(student)){
            throw new RuntimeException("student ["+name+"] not fund!");
        }
        mockDelay();
        student.getCourses(hazelcastInstance).add(course);
        return student;
    }

    @GetMapping("/students")
    public String getAll(){
        StringBuilder sb = new StringBuilder();
        studentIMap.values()
                .forEach(stu -> {
                    sb.append(stu.getName());
                    sb.append(" => ");
                    stu.getCourses(hazelcastInstance)
                            .forEach(course -> sb.append(course).append(" , "));
                    sb.append("\n");
                });
        return sb.toString();
    }

    /**
     * mock 延迟
     * @throws InterruptedException
     */
    private void mockDelay() throws InterruptedException{
        Random random = new Random();
        long timeout = 3000 + random.nextInt(100);
        System.out.println(System.currentTimeMillis()+" , timeout "+timeout);
        TimeUnit.MILLISECONDS.sleep(timeout);
        System.out.println(System.currentTimeMillis()+" , timeout "+timeout);
    }

    private static class Student implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getCourses(HazelcastInstance hazelcastInstance) {
            return hazelcastInstance.getList(name+"-cousers");
        }
    }
}
