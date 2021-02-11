package publisher;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static String TOPIC = "log_analyzer";
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerExample.class);

    public static void main(String[] args) {
        new Thread(KafkaConsumerExample::consumerListener).start();
    }

    public static void consumerListener() {
        final Consumer<String, String> consumer = createConsumer();

        final int giveUp = 100;
        int noRecordsCount = 0;
        while (true) {
            final ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(1));

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                logger.info("Consumer Record:({}, {}, {}, {})\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");

    }

    private static Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }


}
