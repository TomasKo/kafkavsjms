<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">project_title</h3>

  <p align="center">
    project_description
    <br />
    <a href="https://github.com/github_username/repo_name"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/github_username/repo_name">View Demo</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues">Report Bug</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

<p align="right">(<a href="#readme-top">back to top</a>)</p>






<!-- GETTING STARTED -->
## Getting Started



### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

```ruby
require 'redcarpet'
markdown = Redcarpet.new("Hello World!")
puts markdown.to_html
```

# Kafka Configuration

## Intro
In this section is high-level overview of Apache Kafka configuration and snippets witch should help to understand basic configuration.

### In Spring Boot
#### Configuration dependency
In Spring Boot the easiest way to implement Kafka is in maven dependency build toll declare:
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```
For the minimal consumer application is needed to implement:

```java
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "id", topics = "topic1")
    public void listen(String in) {
        //business logic 
        System.out.println(in);
    }

}
```
For the minimal producer application is needed to implement:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("topic1", "test");
        };
    }

}
```
### Without Spring Boot
Now lets see the same simple implementation without use Spring Boot.
#### Configuration dependency
Spring Boot automatically declare the correct version which is compatible with your Boot version. 
If we are not using Spring Boot, declare the kafka jar as a dependency:
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.9.1</version>
</dependency>
```

```java
public class Sender {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		context.getBean(Sender.class).send("test message", 1);
	}

	private final KafkaTemplate<Integer, String> template;

	public Sender(KafkaTemplate<Integer, String> template) {
		this.template = template;
	}

	public void send(String toSend, int key) {
		this.template.send("topic1", key, toSend);
	}

}

public class Listener {

    @KafkaListener(id = "listener1", topics = "topic1")
    public void listener1(String in) {
        //business logic 
        System.out.println(in);
    }

}

@Configuration
@EnableKafka
public class Config {

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String>
                        kafkaListenerContainerFactory(ConsumerFactory<Integer, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // ...
        return props;
    }

    @Bean
    public Sender sender(KafkaTemplate<Integer, String> template) {
        return new Sender(template);
    }

    @Bean
    public Listener listener() {
        return new Listener();
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //...
        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTemplate<Integer, String>(producerFactory);
    }

}
```

As the example shows, that without Spring Boot is need to be defined several infrastructure beans

### Without Spring
Now lets see simple usage of Kafka consumer without using Spring.

```java
        Properties props=new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
        props.setProperty("group.id","group1");
        props.setProperty("enable.auto.commit","true");
        props.setProperty("auto.commit.interval.ms","1000");
        props.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        
        KafkaConsumer<String, String> consumer=new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("topic1","topic2"));
        while(true){
          ConsumerRecords<String, String> records=consumer.poll(Duration.ofMillis(100));
          for(ConsumerRecord<String, String> record:records){
                System.out.printf("offset = %s, key = %s, value = %s",record.offset(),record.key(),record.value());
          }   
        }

```
Setting enable.auto.commit means that offsets are committed automatically with a frequency controlled by the config auto.commit.interval.ms.

In this example the consumer is subscribing to the topics topic1 and topic2 as part of a group of consumers called group1 as configured with group.id.

The deserializer settings specify how to turn bytes into objects. For example, by specifying string deserializers, we are saying that our record's key and value will just be simple strings.

<!-- ROADMAP -->
## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- LICENSE -->
## License



<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Tomas Kopernicky - tomas.kopernicky@betterask.erni

Project Link: [https://github.com/TomasKo/seniorprogram](https://github.com/TomasKo/seniorprogram)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
