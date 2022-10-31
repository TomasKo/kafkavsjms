package seniorprogram.ordering.kafka.notordered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaNotOrderedApplication {




    public static void main(String[] args)  throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaNotOrderedApplication.class, args);

        KafkaMessageProducer producer1 = context.getBean(KafkaMessageProducer.class);
        KafkaMessageProducer producer2 = context.getBean(KafkaMessageProducer.class);
        KafkaMessageProducer producer3 = context.getBean(KafkaMessageProducer.class);
        KafkaMessageListener listener = context.getBean(KafkaMessageListener.class);
        Thread.currentThread().sleep(10000); //wait for listener to register to topic

        listener.latch.await(1, TimeUnit.SECONDS);

        Runnable taskProducer1 = () -> sendToTopic(KafkaTopicConfig.TOPIC_NAME,producer1, 1,5);
        Runnable taskProducer2 = () -> sendToTopic(KafkaTopicConfig.TOPIC_NAME,producer2, 6,10);
        Runnable taskProducer3 = () -> sendToTopic(KafkaTopicConfig.TOPIC_NAME,producer3, 11,15);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(taskProducer1);
        executorService.submit(taskProducer2);
        executorService.submit(taskProducer3);

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        Thread.currentThread().sleep(5000); //wait for processing
        context.close();
        System.out.println("Finish");
    }

    private static void sendToTopic(String topic, KafkaMessageProducer producer, int idItemFrom, int idItemTo) {
        for (int i =idItemFrom; i<=idItemTo;i++){
            producer.sendMessageWithCallBack(topic, "mesageId="+i);
        }
    }

    @Bean
    public KafkaMessageProducer messageProducer() {
        return new KafkaMessageProducer();
    }

}
