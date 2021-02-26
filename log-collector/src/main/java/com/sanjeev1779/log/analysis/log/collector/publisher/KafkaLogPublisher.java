package com.sanjeev1779.log.analysis.log.collector.publisher;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sanjeev1779.log.analysis.common.dtos.LogMessageDto;
import com.sanjeev1779.log.analysis.common.utils.SerializationUtil;
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
        start();
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

    private synchronized void start() {
        if (producer != null) {
            return;
        }
        producer = new KafkaProducer<>(getProducerConfig());
    }

    public synchronized void stop() {
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
        Runnable runnable = (() -> {
            try {
                LogMessageDto logMessage = new LogMessageDto();
                logMessage.setClassName(loggingEvent.getLoggerName());
                logMessage.setTimestamp(loggingEvent.getTimeStamp());
                logMessage.setLogLevel(loggingEvent.getLevel().levelStr);
                logMessage.setMessage(loggingEvent.getMessage());

                publishMsg(logMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.execute(runnable);
        return true;
    }

    public boolean publishMsg(LogMessageDto logMessage) {
        if (producer == null) {
            return false;
        }
        String kafkaMsg = SerializationUtil.writeString(logMessage);
        final ProducerRecord<String, String> record = new ProducerRecord<>(kafkaLogTopic, kafkaMsg);
        producer.send(record, new AsyncKafkaRecordPublishCallback());
        return true;
    }

    public static class AsyncKafkaRecordPublishCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
}