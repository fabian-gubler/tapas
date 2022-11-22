package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a template for implementing an HTTP adapter that places bid.
 */
@Component
@Profile("http-websub")
public class PlaceBidForAuctionHttpAdapter implements PlaceBidForAuctionPort {
    private static final Logger LOGGER = LogManager.getLogger(PlaceBidForAuctionHttpAdapter.class);

    @Override
    public void placeBid(Auction auction, Bid bid) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create(auction.getAuctionHouseUri() + "/" + auction.getAuctionId()))
                .header("content-type", BidJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(BidJsonRepresentation.serialize(bid)))
                .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
