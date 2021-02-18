package com.sanjeev1779.log.collector.publisher;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaLogPublisher implements LogPublisher {
    private static final String kafkaLogTopic = "log_analyzer";
    private static Producer<String, String> producer;
    private static KafkaLogPublisher kafkaLogPublisher;
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    private KafkaLogPublisher() {
    }

    public static KafkaLogPublisher getInstance() {
        synchronized (KafkaLogPublisher.class) {
            if (kafkaLogPublisher == null) {
                kafkaLogPublisher = new KafkaLogPublisher();
            }
        }
        return kafkaLogPublisher;
    }

    private Properties getProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 1);
        props.put("batch.size", 1638);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554);
        props.put("max.block.ms", 2000);
        props.put("request.timeout.ms", 2000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public void start() {
        if (producer != null) {
            return;
        }
        producer = new KafkaProducer<>(getProducerConfig());
    }

    public void stop() {
        if (producer == null) {
            return;
        }
        try {
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean publish(ILoggingEvent loggingEvent) {
        try {
            return publishMsg(loggingEvent.getClass().getName(), loggingEvent.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean publishMsg(String key, String msg) {
        if (producer == null) {
            return false;
        }
        final ProducerRecord<String, String> record =
                new ProducerRecord<>(kafkaLogTopic, key,
                        msg);
        Runnable runnable = () -> {
            try {
                producer.send(record, new AsyncKafkaRecordPublishCallback()).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable);
        return true;
    }

    public static class AsyncKafkaRecordPublishCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
            } else {
                System.out.printf("meta(topic=%s partition=%d, offset=%d)\n",
                        recordMetadata.topic(), recordMetadata.partition(),
                        recordMetadata.offset());
            }
        }
    }
}