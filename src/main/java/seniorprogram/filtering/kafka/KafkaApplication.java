package seniorprogram.filtering.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaApplication {


    public static void main(String[] args)  throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

        KafkaMessageProducer producer = context.getBean(KafkaMessageProducer.class);
        KafkaMessageListener listener = context.getBean(KafkaMessageListener.class);
        producer.sendMessage("topic1", "Hello, World!");

        context.close();
    }

    @Bean
    public KafkaMessageProducer messageProducer() {
        return new KafkaMessageProducer();
    }

}
