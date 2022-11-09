package seniorprogram.groups.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaMessageListener {

    public CountDownLatch g1Latch = new CountDownLatch(2);
    public CountDownLatch g2Latch = new CountDownLatch(2);

    @KafkaListener(topics = "topic1", containerFactory = "group1KafkaListenerContainerFactory")
    public void listenGroup1(String message) {
        System.out.println("Received Message in group1 listener: " + message);
        this.g1Latch.countDown();
    }

    @KafkaListener(topics = "topic1", containerFactory = "group2KafkaListenerContainerFactory")
    public void listenGroup2(String message) {
        System.out.println("Received Message in group2 listener: " + message);
        this.g2Latch.countDown();
    }


}