package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
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
public class BidReceivedEventListenerMqttAdapter implements AuctionHouseEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(BidReceivedEventListenerMqttAdapter.class);
    //private final BidReceivedEventHandler bidReceivedEventHandler;

    // You can use this object to retrieve properties from application.properties, e.g. the
    @Autowired
    private ConfigProperties config;

    private final BidReceivedEventHandler bidReceivedEventHandler;

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());
        LOGGER.info("Received bid event via MQTT: " + payload);

        try {
            Bid bid = BidJsonRepresentation.deserialize(payload);

            BidReceivedEvent bidReceivedEvent = new BidReceivedEvent(bid);
            bidReceivedEventHandler.handleBidReceivedEvent(bidReceivedEvent);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
