package com.izhengyin.springboot.example.hazelcast.controller;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.topic.ITopic;
import org.checkerframework.common.value.qual.IntRange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-28 11:47
 */

@RestController
public class CommandController {

    private final HazelcastInstance hazelcastInstance;
    private final ITopic<String> testTopic;
    private final IMap<String,String> testMap;

    public CommandController(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
        this.testTopic = this.hazelcastInstance.getTopic("tTopic");
        this.testMap = this.hazelcastInstance.getMap("tMap");

        IntStream.range(0,10000)
                .forEach(i ->  this.testMap.put("key"+i,i+""));
    }

    @PostMapping("/put")
    public String put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        return testMap.put(key, value);
    }

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        return testMap.get(key);
    }


    @GetMapping("/size")
    public int size() {
        return testMap.size();
    }

    @GetMapping("/all")
    public Map<String,String> all() {
        return testMap;
    }

    @PostMapping("/publish")
    public String publish(){
        testTopic.publish("hello");
        return null;
    }

    @PostConstruct
    public void subscribe(){
        testTopic.addMessageListener(message -> System.out.println("Receive topic[test] message => "+message.getMessageObject()));
    }

}