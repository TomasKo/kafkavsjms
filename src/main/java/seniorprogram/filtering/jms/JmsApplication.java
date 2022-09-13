package seniorprogram.filtering.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

@ComponentScan
public class JmsApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(JmsApplication.class, args);



        JmsTemplate jms = context.getBean(JmsTemplate.class);
       // jms.setDeliveryPersistent(true);

        JmsMessageProducer producer = new JmsMessageProducer();
        producer.setJmsTemplate(jms);

        producer.sendTextMessage("topic-2", "basic");
        producer.sendTextMessage("queue-2", "basic");

        producer.sendTextMessageWithProperty("queue-3", "filtered", "hight");
        producer.sendTextMessageWithProperty("queue-3", "message2", "low");
        producer.sendTextMessage("queue-2", "massage3");

        context.close();
    }
}