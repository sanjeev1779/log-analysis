package com.sanjeev1779.log.analysis.log.collector.publisher;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface LogPublisher {
    boolean publish(ILoggingEvent loggingEvent);
}
