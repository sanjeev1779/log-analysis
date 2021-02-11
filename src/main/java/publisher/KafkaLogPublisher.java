package publisher;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class KafkaLogPublisher implements LogPublisher {
////    private final Producer<String, String> producer;
    private final String kafkaLogTopic = "log_analyzer";
//
//    public KafkaLogPublisher() {
////        producer = new KafkaProducer<>(getProducerConfig());
//    }
//
//    private Properties getProducerConfig() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
////        props.put("acks", "1");
////        props.put("retries", 1);
////        props.put("batch.size", 1638);
////        props.put("linger.ms", 1);
////        props.put("buffer.memory", 33554);
//        props.put("max.block.ms", 2000);
////        props.put("request.timeout.ms", 2000);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        return props;
//    }

    @Override
    public boolean publish(ILoggingEvent loggingEvent) {
        try {
            Producer<String, String> producer = KafkaConsumerExample.createProducer();
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    kafkaLogTopic,
                    loggingEvent.getClass().getName(),
                    loggingEvent.getMessage());
            producer.send(producerRecord);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
