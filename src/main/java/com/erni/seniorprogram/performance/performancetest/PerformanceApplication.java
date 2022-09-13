package com.erni.seniorprogram.performance.performancetest;

import com.erni.seniorprogram.performance.jms.JmsMessageProducer;
import com.erni.seniorprogram.performance.kafka.KafkaMessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

@ComponentScan(basePackages = { "com.erni.seniorprogram.performance.jms", "com.erni.seniorprogram.performance.kafka"  })
public class PerformanceApplication {

    public static void main(String[] args) {
       //ApplicationContext context = new AnnotationConfigApplicationContext(PerformanceApplication.class);
        ConfigurableApplicationContext context = SpringApplication.run(PerformanceApplication.class, args);



        JmsTemplate jms = context.getBean(JmsTemplate.class);

        JmsMessageProducer jmsProducer = new JmsMessageProducer();
        jmsProducer.setJmsTemplate(jms);

        int[] countSet = {1,100,1000,10000};
        for (int n : countSet) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                jmsProducer.sendTextMessage("queue-1", "test message=" + i);
            }
            long finishJms = (System.currentTimeMillis() - start);


            KafkaMessageProducer kafkaProducer = context.getBean(KafkaMessageProducer.class);


            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                kafkaProducer.sendMessage("topic1", "message_" + i);
            }
            long finishKafka = (System.currentTimeMillis() - start);

            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                kafkaProducer.sendAcksMessage("topic2", "message_" + i);
            }
            long finishAcksKafka = (System.currentTimeMillis() - start);


            System.out.println(String.format("JMS send count:%s time spend:%s  ", n, finishJms));
            System.out.println(String.format("Kafka send count:%s time spend:%s  ", n, finishKafka));
            System.out.println(String.format("ACKS Kafka send count:%s time spend:%s  ", n, finishAcksKafka));

        }

        context.close();
    }
}