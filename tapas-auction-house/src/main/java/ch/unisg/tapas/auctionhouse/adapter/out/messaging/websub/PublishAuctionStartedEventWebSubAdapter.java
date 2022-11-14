package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for notifying a WebSub Hub that a new auction was started.
 */
@Component
@Profile("http-websub")
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {
    private static final Logger LOGGER = LogManager.getLogger(PublishAuctionStartedEventWebSubAdapter.class);

    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        LOGGER.info("Publishing auction started event via WebSub");

        // TODO: send request to notify the hub of new content (cf. WebSub protocol)
    }
}
