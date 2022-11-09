package seniorprogram.groups.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableKafka
public class KafkaApplication {


    public static void main(String[] args)  throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

        KafkaMessageProducer producer = context.getBean(KafkaMessageProducer.class);
        KafkaMessageListener listener = context.getBean(KafkaMessageListener.class);
        Thread.currentThread().sleep(5000); //wait for listener
        producer.sendMessage("topic1", "Message: Hello, World!");

        listener.g1Latch.await(1, TimeUnit.SECONDS);
        listener.g2Latch.await(1, TimeUnit.SECONDS);
        context.close();
    }

    @Bean
    public KafkaMessageProducer messageProducer() {
        return new KafkaMessageProducer();
    }

}
