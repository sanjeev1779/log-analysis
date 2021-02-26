package com.sanjeev1779.log.analysis.log.collector.appenders;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaBasedAppenderTest {
    private static final Logger logger = LoggerFactory.getLogger(KafkaBasedAppenderTest.class);

    @Test
    public void testLog() {
        logger.error("hiiiii");
    }
}
