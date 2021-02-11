package com.sanjeev1779.log.analysis.appenders;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import publisher.KafkaLogPublisher;
import publisher.LogPublisher;

public class KafkaBasedAppender extends AppenderBase<ILoggingEvent> {
    private final Level minLogLevelForLogging = Level.ERROR;

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        if (loggingEvent.getLevel().levelInt < minLogLevelForLogging.levelInt) {
            return;
        }

        LogPublisher logPublisher = new KafkaLogPublisher();
        logPublisher.publish(loggingEvent);
    }

}
