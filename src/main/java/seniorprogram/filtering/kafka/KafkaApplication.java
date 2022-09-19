package seniorprogram.filtering.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableKafkaStreams
@EnableKafka
public class KafkaApplication {


    public static void main(String[] args)  throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

        KafkaMessageProducer producer = context.getBean(KafkaMessageProducer.class);
        KafkaMessageListener listener = context.getBean(KafkaMessageListener.class);
        producer.sendMessage("topic2", "Message: Hello, World!");
        producer.sendMessage("topic2", "Filter me No!");
        producer.sendMessage("topic2", "Message Filter me a");
        listener.filterLatch.await(10, TimeUnit.SECONDS);
        context.close();
    }

    @Bean
    public KafkaMessageProducer messageProducer() {
        return new KafkaMessageProducer();
    }

}
