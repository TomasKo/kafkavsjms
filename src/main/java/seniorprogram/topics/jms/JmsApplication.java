package seniorprogram.topics.jms;

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

        producer.sendTextMessage("topic-1", "m1");
        producer.sendTextMessage("topic-1", "m2");
        producer.sendTextMessage("topic-1", "massage3");

        context.close();
    }
}