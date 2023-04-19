package seniorprogram.filtering.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JmsMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    @JmsListener(destination = "queue-2")
    public void sampleJmsListenerMethod(TextMessage message) throws JMSException {
        logger.info(String.format("---- Listener1 Received text message: %s ---------", message.getText()));
    }

    @JmsListener(destination = "queue-3", selector = "myproperty = 'hight'")
    public void sampleJmsListenerMethod2(TextMessage message) throws JMSException {
        logger.info(String.format("---- Listener2 Received text message: %s ---------", message.getText()));
    }
}
