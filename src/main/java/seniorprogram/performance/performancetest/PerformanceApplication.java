package seniorprogram.performance.performancetest;

import seniorprogram.performance.jms.JmsMessageProducer;
import seniorprogram.performance.kafka.KafkaMessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

@ComponentScan(basePackages = { "seniorprogram.performance.jms", "seniorprogram.performance.kafka"  })
public class PerformanceApplication {

    public static void main(String[] args) {
       //ApplicationContext context = new AnnotationConfigApplicationContext(PerformanceApplication.class);
        ConfigurableApplicationContext context = SpringApplication.run(PerformanceApplication.class, args);



        JmsTemplate jms = context.getBean(JmsTemplate.class);

        JmsMessageProducer jmsProducer = new JmsMessageProducer();
        jmsProducer.setJmsTemplate(jms);

        int[] countSet = {1,100,1000,10000};
        int tryCountAverage = 3;
        long kafkaAverage= 0l;
        long jmsAverage= 0l;
        for (int n : countSet) {

            for (int run=0;run<tryCountAverage;run++) {
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


                System.out.println(String.format("JMS send count:%s time spend:%s  ", n, finishJms));
                System.out.println(String.format("Kafka send count:%s time spend:%s  ", n, finishKafka));
                jmsAverage+=finishJms;
                kafkaAverage+=finishKafka;
            }
            System.out.println(String.format("Average JMS send count:%s time spend:%s  ", n, jmsAverage/tryCountAverage));
            System.out.println(String.format("Average Kafka send count:%s time spend:%s  ", n, kafkaAverage/tryCountAverage));

        }

        context.close();
    }
}