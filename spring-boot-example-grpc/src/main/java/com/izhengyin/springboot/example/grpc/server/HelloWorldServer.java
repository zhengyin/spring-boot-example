package com.izhengyin.springboot.example.grpc.server;

import com.izhengyin.springboot.example.grpc.helloworld.GreeterGrpc;
import com.izhengyin.springboot.example.grpc.helloworld.HelloReply;
import com.izhengyin.springboot.example.grpc.helloworld.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-26 14:03
 */
@Service
public class HelloWorldServer {
    public static int PORT = 50051;
    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

    private Server server;

    public void start() throws IOException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(PORT)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info(" Server started, listening on " + server.getPort());
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}