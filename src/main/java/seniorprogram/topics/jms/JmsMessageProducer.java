package seniorprogram.topics.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class JmsMessageProducer {


    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(final Object message) {
        this.jmsTemplate.convertAndSend(message);
    }

    public void sendTextMessage(String destination, String message) {
        jmsTemplate.setDeliveryPersistent(true);
        //logger.info(String.format("Sending message to: %s message: %s", destination, message));
        jmsTemplate.send(destination, s -> s.createTextMessage(message));
    }


}