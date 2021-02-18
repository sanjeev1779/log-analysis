package com.sanjeev1779.log.analysis.log.collector.appenders;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.sanjeev1779.log.analysis.log.collector.publisher.KafkaLogPublisher;

public class KafkaBasedAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        KafkaLogPublisher.getInstance().publish(loggingEvent);
    }

}
