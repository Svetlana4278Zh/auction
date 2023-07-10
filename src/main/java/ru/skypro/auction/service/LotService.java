package ru.skypro.auction.service;

import ru.skypro.auction.dto.*;

import java.util.List;

public interface LotService {
    BidDTO getFirstBidder(int id);

    BidDTO getMostFrequentBidder(int id);

    FullLot getFullLot(int id);

    void start(int id);

    void createBid(int id, BidDTO bidDTO);

    void stop(int id);

    LotDTO createLot(CreateLot newLot);

    List<LotDTO> findLots(Status status, int page);

    byte[] getCSVFile();
}
