package com.izhengyin.springboot.example.grpc;

import com.izhengyin.springboot.example.grpc.server.HelloWorldServer;
import com.izhengyin.springboot.example.grpc.server.ManualFlowControlServer;
import com.izhengyin.springboot.example.grpc.server.RouteGuideServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-26 10:14
 */
@SpringBootApplication
public class GrpcApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GrpcApplication.class);
        springApplication.addListeners(new MyApplicationListener());
        springApplication.run(args);
    }

    public static class MyApplicationListener implements ApplicationListener<ApplicationStartedEvent>{
        @Override
        public void onApplicationEvent(ApplicationStartedEvent event) {
            runHelloWorldServer(event);
            runManualFlowControlServer(event);
            runRouteGuideServer(event);
        }

        /**
         * 运行 helloWorldServer
         * @param event
         */
        private void runHelloWorldServer(ApplicationStartedEvent event){
            final HelloWorldServer server = event.getApplicationContext().getBean(HelloWorldServer.class);
            try {
                server.start();
            }catch (Exception e){
                e.printStackTrace();
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    try {
                        server.stop();
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }
                    System.err.println("*** server shut down");
                }
            });
        }

        /**
         * 运行 ManualFlowControlServer
         * @param event
         */
        private void runManualFlowControlServer(ApplicationStartedEvent event){
            final ManualFlowControlServer server = event.getApplicationContext().getBean(ManualFlowControlServer.class);
            try {
                server.start();
            }catch (Exception e){
                e.printStackTrace();
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                    System.err.println("Shutting down");
                    try {
                        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }
                }
            });
        }


        private void runRouteGuideServer(ApplicationStartedEvent event){
            final RouteGuideServer server = event.getApplicationContext().getBean(RouteGuideServer.class);
            try {
                server.start();
            }catch (Exception e){

            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                    System.err.println("Shutting down");
                    try {
                        server.stop();
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }
                }
            });

        }
    }


}
