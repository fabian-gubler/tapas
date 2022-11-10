package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionHouseEventMqttDispatcher;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionStartedEventListenerMqttAdapter;
import ch.unisg.tapas.auctionhouse.application.port.out.feeds.SubscribeToAuctionFeedPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.common.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mqtt")
@RequiredArgsConstructor
public class SubscribeToAuctionFeedMqttAdapter implements SubscribeToAuctionFeedPort {
    private static final Logger LOGGER = LogManager.getLogger(SubscribeToAuctionFeedMqttAdapter.class);

    private final AuctionHouseEventMqttDispatcher dispatcher;
    private final AuctionStartedEventListenerMqttAdapter auctionStartedEventListenerMqttAdapter;

    @Autowired
    private final ConfigProperties config;

    @Override
    public void subscribeToFeed(Auction.AuctionFeedId feedId) {
        LOGGER.info("Subscribing to auction feed via MQTT: " + feedId.getValue());

        // TODO: Subscribe to an auction feed via MQTT
        // 1. Register the auction feed topic and the listener with the MQTT event dispatcher
        // 2. Retrieve an instance of the TAPAS MQTT client
        // 3. Use the TAPAS MQTT client to subscribe to the topic
    }
}
