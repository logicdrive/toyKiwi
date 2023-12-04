package toykiwi.infra;

import toykiwi.ExternalSystemProxyApplication;
import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

// Kafka 이벤트들을 관리하기 위한 기반 클래스
public class AbstractEvent {
    String eventType;
    Long timestamp;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    // 생성된 이벤트를 Kafka로 발행시키기 위해서
    public void publish() {
        KafkaProcessor processor = ExternalSystemProxyApplication.applicationContext.getBean(
            KafkaProcessor.class
        );
        MessageChannel outputChannel = processor.outboundTopic();
        
        CustomLogger.debug(CustomLoggerType.EFFECT, "Publish event", String.format("{event: %s}", this.toString()));
        outputChannel.send(
            MessageBuilder
                .withPayload(this)
                .setHeader(
                    MessageHeaders.CONTENT_TYPE,
                    MimeTypeUtils.APPLICATION_JSON
                )
                .setHeader("type", getEventType())
                .build()
        );
    }

    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    AbstractEvent.this.publish();
                }
            }
        );
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }
}
