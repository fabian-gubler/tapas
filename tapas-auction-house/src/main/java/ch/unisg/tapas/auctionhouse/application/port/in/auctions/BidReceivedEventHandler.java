package ch.unisg.tapas.auctionhouse.application.port.in.auctions;

public interface BidReceivedEventHandler {

    boolean handleBidReceivedEvent(BidReceivedEvent bidReceivedEvent);
}
