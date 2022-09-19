package seniorprogram.filtering.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KafkaEventStreamProcessor {

    @Autowired
    private StreamsBuilder streamsBuilder;

    @PostConstruct
    public void streamTopology(){
        KStream<String, String> kStream =
                streamsBuilder.stream("topic2", Consumed.with(Serdes.String(), Serdes.String()));
        kStream.filter((key, value) -> value.startsWith("M") )
                .peek((k, v) -> System.out.println("Filtering message:"+v))
                .to("topic1", Produced.with(Serdes.String(), Serdes.String()));
    }
}
