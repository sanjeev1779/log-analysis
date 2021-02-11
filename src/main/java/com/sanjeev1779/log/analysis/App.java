package com.sanjeev1779.log.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import publisher.KafkaConsumerExample;

import java.util.UUID;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        new Thread(KafkaConsumerExample::consumerListener).start();

        int max = 1;
        for (int i = 0; i < max;i++) {
            logger.error("error logging = " + UUID.randomUUID() );
        }

    }
}
