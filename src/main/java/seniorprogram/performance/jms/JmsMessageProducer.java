package seniorprogram.performance.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class JmsMessageProducer {

  //  private static final Logger logger = LoggerFactory.getLogger(JmsMessageProducer.class);


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