package com.sanjeev1779.log.collector.appenders;

import com.sanjeev1779.log.collector.publisher.KafkaLogPublisher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaBasedAppenderTest {
    private static final Logger logger = LoggerFactory.getLogger(KafkaBasedAppenderTest.class);

    @Test
    public void testLog() {
        KafkaLogPublisher.getInstance().start();
        logger.error("hiiiii");
    }
}
