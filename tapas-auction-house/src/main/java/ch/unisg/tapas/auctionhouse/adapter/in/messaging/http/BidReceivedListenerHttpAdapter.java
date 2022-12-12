package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@Profile("http-websub")
@RequiredArgsConstructor
@RestController
public class BidReceivedListenerHttpAdapter {

    private static final Logger LOGGER = LogManager.getLogger(BidReceivedListenerHttpAdapter.class);
    private final BidReceivedEventHandler bidReceivedEventHandler;

    @PostMapping(path = "/auctions/{auctionId}", consumes = BidJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<Void> handeBidReceivedEvent(@PathVariable("auctionId") String auctionId, @RequestBody String payload) {

        try {
            Bid bid = BidJsonRepresentation.deserialize(payload);
            LOGGER.info("Bid placed for auction #" + auctionId + " by " + bid.getBidderName().getValue());

            BidReceivedEvent bidReceivedEvent = new BidReceivedEvent(bid);
            bidReceivedEventHandler.handleBidReceivedEvent(bidReceivedEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
