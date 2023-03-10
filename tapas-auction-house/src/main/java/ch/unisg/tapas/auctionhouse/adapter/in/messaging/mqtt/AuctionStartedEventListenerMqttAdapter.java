package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt.PlaceBidForAuctionMqttAdapter;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import ch.unisg.tapas.common.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
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

    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());
        LOGGER.info("Received auction started event via MQTT: " + payload);

        try {
            Auction auction = AuctionJsonRepresentation.deserialize(payload);

            if (auction.getAuctionHouseUri().getValue().toString().equals(config.getAuctionHouseUri().toString())) {
                // return as we can't handle our own auctions!
                System.out.println("Auction is our own auction, return silently!");
                return false;
            }

            AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(auction);
            auctionStartedEventHandler.handleAuctionStartedEvent(auctionStartedEvent);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
