package nats;

import io.nats.client.*;
import io.nats.streaming.*;
import io.nats.streaming.Options;


import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Hello world!
 *
 */
public class Publish {
    public static void main( String[] args ) throws IOException, InterruptedException, TimeoutException {
        String result = "nats发送消息成功";
//      Options streamingOption = createStreamingOption();
//      StreamingConnection sc = NatsStreaming.connect("test-cluster", "bar", streamingOption);
        // 第一个参数表示clusterId,在启动NATS Streaming容器的时候确定
        // 第二个参数表示clientID,连接客户端的唯一标识符
        StreamingConnectionFactory cf = new StreamingConnectionFactory("cluster", "bar");
        cf.setNatsConnection(Nats.connect("nats://127.0.0.1:4222"));
        StreamingConnection sc = cf.createConnection();
         System.out.println(result);
         while (true){
             sc.publish("subject",result.getBytes());
         }
    }

    public static Options createStreamingOption(){
        Options.Builder streamingOptions = new Options.Builder().natsUrl("nats://127.0.0.1:4222").errorListener(new ErrorListener() {
            @Override
            public void errorOccurred(Connection connection, String s) {
                 System.out.println("a" + s);
            }
            @Override
            public void exceptionOccurred(Connection connection, Exception e) {
                System.out.println("b" + e);
            }
            @Override
            public void slowConsumerDetected(Connection connection, Consumer consumer) {
                System.out.println("c" + consumer);
            }
        }).connectionListener(new ConnectionListener() {
            @Override
            public void connectionEvent(Connection connection, Events events) {
                System.out.println("d" + events);
            }
        });
        return streamingOptions.build();
    }
}
