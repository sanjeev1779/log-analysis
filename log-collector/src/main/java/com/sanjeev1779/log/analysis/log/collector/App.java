package com.sanjeev1779.log.analysis.log.collector;

import com.sanjeev1779.log.analysis.log.collector.publisher.KafkaLogPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        KafkaLogPublisher.getInstance().start();

        logger.error("hiiiii");
    }
}
