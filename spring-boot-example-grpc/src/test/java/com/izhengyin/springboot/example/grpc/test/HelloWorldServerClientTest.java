package com.izhengyin.springboot.example.grpc.test;

import com.izhengyin.springboot.example.grpc.helloworld.GreeterGrpc;
import com.izhengyin.springboot.example.grpc.helloworld.HelloReply;
import com.izhengyin.springboot.example.grpc.helloworld.HelloRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-26 14:08
 */
public class HelloWorldServerClientTest {

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    @Test
    public void test() throws Exception {
        String user = "world";
        // Access a service running on the local machine on port 50051
        String target = "127.0.0.1:50051";
        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            Assert.assertEquals("Hello "+user,client.greet(user));
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }


    public static class HelloWorldClient {
        private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

        private final GreeterGrpc.GreeterBlockingStub blockingStub;

        /** Construct client for accessing HelloWorld server using the existing channel. */
        public HelloWorldClient(Channel channel) {
            // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
            // shut it down.

            // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
            blockingStub = GreeterGrpc.newBlockingStub(channel);
        }

        /** Say hello to server. */
        public String greet(String name) {
            logger.info("Will try to greet " + name + " ...");
            HelloRequest request = HelloRequest.newBuilder().setName(name).build();
            HelloReply response;
            try {
                response = blockingStub.sayHello(request);
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return null;
            }
            return response.getMessage();
        }
    }
}
