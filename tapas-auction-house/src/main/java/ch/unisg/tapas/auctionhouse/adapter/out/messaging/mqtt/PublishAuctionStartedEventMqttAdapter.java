package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionHouseEventMqttDispatcher;
import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for publishing auction started events via MQTT.
 */
@Component
@Profile("mqtt")
@RequiredArgsConstructor
public class PublishAuctionStartedEventMqttAdapter implements AuctionStartedEventPort {
    private static final Logger LOGGER = LogManager.getLogger(PublishAuctionStartedEventMqttAdapter.class);


    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        LOGGER.info("Publishing auction started event via MQTT");

        TapasMqttClient mqttClient = TapasMqttClient.getInstance();
        try {
            mqttClient.publish("ch/unisg/tapas/auctions",
                AuctionJsonRepresentation.serialize(event.getAuction()));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
