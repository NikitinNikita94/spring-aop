package t1.springaop.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.aop")
public record TrackTimeProperties(Boolean isLoggingTrackTime) {
}
