package publisher;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface LogPublisher {
    boolean publish(ILoggingEvent loggingEvent);
}
