package seniorprogram.ordering.kafka.ordered;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaMessageListener {

    public CountDownLatch latch = new CountDownLatch(3);


    @KafkaListener(topics = "topic1", groupId = "group1", containerFactory = "group1KafkaListenerContainerFactory")
    public void listenGroupGroup1(String message) {
        System.out.println("Received Message in group 'group1': " + message);
        latch.countDown();
    }


}