package seniorprogram.filtering.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {


    private String topicName= "topic1";

    private String topic2Name= "topic2";

    private String longMsgTopicName= "longmessage";

    private String partitionedTopicName="partitioned";

    private String filteredTopicName="filtered";

    private String greetingTopicName="greeting";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(topic2Name, 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(partitionedTopicName, 6, (short) 1);
    }

    @Bean
    public NewTopic topic4() {
        return new NewTopic(filteredTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic5() {
        return new NewTopic(greetingTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic6() {
        NewTopic newTopic = new NewTopic(longMsgTopicName, 1, (short) 1);
        Map<String, String> configs = new HashMap<>();
        configs.put("max.message.bytes", "20971520");
        newTopic.configs(configs);
        return newTopic;
    }
}