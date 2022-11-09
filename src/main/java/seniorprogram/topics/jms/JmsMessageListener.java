package seniorprogram.topics.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JmsMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    @JmsListener(destination = "topic-1")
    public void sampleJmsListenerMethod(TextMessage message) throws JMSException {
        logger.info(String.format("----Listener1 Received text message: %s ---------", message.getText()));
    }

    @JmsListener(destination = "topic-1")
    public void sampleJmsListenerMethod2(TextMessage message) throws JMSException {
        logger.info(String.format("----Listener2 Received text message: %s ---------", message.getText()));
    }
}
