package seniorprogram.filtering.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaApplication {


    public static void main(String[] args)  throws Exception {
        //SpringApplication.run(SeniorprogramApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

        KafkaMessageProducer producer = context.getBean(KafkaMessageProducer.class);
      //  MessageProducer producer2 = context.getBean(MessageProducer.class);
        KafkaMessageListener listener = context.getBean(KafkaMessageListener.class);
        /*
         * Sending a Hello World message to topic 'baeldung'.
         * Must be received by both listeners with group foo
         * and bar with containerFactory fooKafkaListenerContainerFactory
         * and barKafkaListenerContainerFactory respectively.
         * It will also be received by the listener with
         * headersKafkaListenerContainerFactory as container factory.
         */
        String regtopicName;

        String partitionedTopicName;


        producer.sendMessage("topic1", "Hello, World!");
      //  listener.latch.await(10, TimeUnit.SECONDS);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        //    if( i%2==0) {
               //System.out.println("sender1");
                producer.sendMessage("topic1","message_" + i);
        //    }else{
         //       System.out.println("sender2");
         //       producer2.sendMessage("partitioned","message_" + i);
         //   }
        }

        System.out.println("Finish in "+ (System.currentTimeMillis()-start));
        listener.partitionLatch.await(10, TimeUnit.SECONDS);
        /*
         * Sending message to a topic with 5 partitions,
         * each message to a different partition. But as per
         * listener configuration, only the messages from
         * partition 0 and 3 will be consumed.
         */
        for (int i = 0; i < 5; i++) {
            producer.sendMessageToPartition("Hello To Partitioned Topic!", i);
        }
        listener.partitionLatch.await(10, TimeUnit.SECONDS);
/*
        *//*
         * Sending message to 'filtered' topic. As per listener
         * configuration,  all messages with char sequence
         * 'World' will be discarded.
         *//*
        producer.sendMessageToFiltered("Hello Baeldung!");
        producer.sendMessageToFiltered("Hello World!");
        listener.filterLatch.await(10, TimeUnit.SECONDS);

        *//*
         * Sending message to 'greeting' topic. This will send
         * and received a java object with the help of
         * greetingKafkaListenerContainerFactory.
         *//*
       *//* producer.sendGreetingMessage(new Greeting("Greetings", "World!"));
        listener.greetingLatch.await(10, TimeUnit.SECONDS);*//*
*/
        context.close();
    }

    @Bean
    public KafkaMessageProducer messageProducer() {
        return new KafkaMessageProducer();
    }

}
