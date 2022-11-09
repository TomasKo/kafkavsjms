package seniorprogram.performance.kafka;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

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


}