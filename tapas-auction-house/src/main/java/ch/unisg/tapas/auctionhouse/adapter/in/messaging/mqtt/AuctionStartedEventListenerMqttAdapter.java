package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEventHandler;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Listener that handles auction started. This class is only provided as a template.
 */
@Component
@Profile("mqtt")
@RequiredArgsConstructor
public class AuctionStartedEventListenerMqttAdapter implements AuctionHouseEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(AuctionStartedEventListenerMqttAdapter.class);
    private final AuctionStartedEventHandler auctionStartedEventHandler;

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());
        LOGGER.info("Received auction started event via MQTT: " + payload);

        // TODO: handle the auction started event

        return true;
    }
}
