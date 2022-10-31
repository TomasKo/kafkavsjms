package seniorprogram.ordering.kafka.ordered;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;



    public void sendMessage(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
    }


    public void sendMessageToPartition(String topic, String message, int partition) {
        kafkaTemplate.send(topic, partition, null, message);
    }

}