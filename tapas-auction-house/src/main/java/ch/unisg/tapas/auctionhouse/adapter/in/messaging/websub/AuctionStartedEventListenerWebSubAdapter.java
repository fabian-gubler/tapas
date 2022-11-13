package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a template for handling auction started events received via WebSub
 */
@RestController
@Profile("http-websub")
@RequiredArgsConstructor
public class AuctionStartedEventListenerWebSubAdapter {
    private static final Logger LOGGER = LogManager.getLogger(AuctionStartedEventListenerWebSubAdapter.class);
    private final AuctionStartedHandler auctionStartedHandler;

    // TODO: handle WebSub notifications for auction started events
}
