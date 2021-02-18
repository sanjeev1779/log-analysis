package com.sanjeev1779.log.analysis.log.consumer;

import com.sanjeev1779.log.analysis.common.dtos.LogMessageDto;
import com.sanjeev1779.log.analysis.common.utils.SerializationUtil;
import com.sanjeev1779.log.analysis.log.transformers.DefaultLogTransformer;
import com.sanjeev1779.log.analysis.log.transformers.LogTransformer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerProcessor {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String kafkaLogTopic = "log_analyzer";
    private static final String CONSUMER_GROUP = "log-consumer-group-1";
    private static Consumer<String, String> consumer = null;

    static void runConsumer() {
        LogTransformer logTransformer = new DefaultLogTransformer();
        consumer = createConsumer();

        while (true) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
            System.out.println("consumer records = " + consumerRecords.count());

            consumerRecords.forEach((record) -> {
                try {
                    String logEventStr = record.value();
                    LogMessageDto logMessageDto = SerializationUtil.OBJECT_MAPPER.readValue(logEventStr, LogMessageDto.class);
                    logTransformer.process(logMessageDto);
                } catch (Exception e) {
                    System.out.println("error in log for value = " + record.value());
                }
            });

            consumer.commitAsync();
        }
    }

    static void stopConsumer() {
        try {
            consumer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Consumer<String, String> createConsumer() {
        final Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.

        final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(kafkaLogTopic));
        return consumer;
    }

}
