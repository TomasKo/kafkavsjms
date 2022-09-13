package seniorprogram.filtering.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaAcksTemplate;

   /* @Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;
*/


    public void sendMessage(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessageWithCallBack(String topicName, String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendMessageToPartition(String message, int partition) {
        kafkaTemplate.send("partitioned", partition, null, message);
    }

    public void sendMessageToPartition(String message) {
        kafkaTemplate.send("partitioned", null, message);
    }

    public void sendMessageToFiltered(String message) {
        kafkaTemplate.send("filtered", message);
    }

    public void sendAcksMessage(String topicName, String message) {
        kafkaAcksTemplate.send(topicName, message);
    }

/*      public void sendGreetingMessage(Greeting greeting) {
        greetingKafkaTemplate.send(greetingTopicName, greeting);
    }*/

}