package nats;

import io.nats.client.*;
import io.nats.streaming.*;
import io.nats.streaming.Message;
import io.nats.streaming.MessageHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Listening {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

//        sub(args[0],args[1]);
        sub("nats://127.0.0.1:4222","subject");
//        Connection connect = Nats.connect("nats://127.0.0.1:4222");
//        CountDownLatch latch = new CountDownLatch(10);
//        Dispatcher d = connect.createDispatcher((msg) ->{
//            String str = new String(msg.getData(), StandardCharsets.UTF_8);
//            System.out.println(str);
//        });
    }
    public static void sub(String ip, String sub) throws IOException, InterruptedException, TimeoutException {
//        Connection nc = Nats.connect(ip);
        StreamingConnectionFactory cf = new StreamingConnectionFactory("cluster", "bar");
//        StreamingConnection sc = new StreamingConnectionFactory("test-cluster", "bar").createConnection();
        cf.setNatsConnection(Nats.connect("nats://127.0.0.1:4222"));
        StreamingConnection sc = cf.createConnection();
        System.out.println("subject");
        sc.subscribe("subject", msg -> {
            System.out.println(new String(msg.getData(), StandardCharsets.UTF_8));
        }, new SubscriptionOptions.Builder()
                .durableName("aa")
                .deliverAllAvailable().build());
    }
}