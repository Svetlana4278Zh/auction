package ru.skypro.auction.service;

import ru.skypro.auction.dto.BidDTO;
import ru.skypro.auction.dto.FullLot;

public interface LotService {
    BidDTO getFirstBidder(int id);

    BidDTO getMostFrequentBidder(int id);

    FullLot getFullLot(int id);

    void start(int id);

    void createBid(int id, BidDTO bidDTO);
}
