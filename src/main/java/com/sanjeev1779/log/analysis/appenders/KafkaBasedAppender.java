package com.sanjeev1779.log.analysis.appenders;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import publisher.KafkaLogPublisher;

public class KafkaBasedAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        KafkaLogPublisher.getInstance().publish(loggingEvent);
    }

}
