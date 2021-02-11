package com.sanjeev1779.log.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import publisher.KafkaLogPublisher;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        //start kafka producer
        KafkaLogPublisher.getInstance().start();

        int max = 10;
        for (int i = 0; i < max; i++) {
            logger.error("error logging = " + UUID.randomUUID());
        }

        KafkaLogPublisher.getInstance().stop();
    }
}
