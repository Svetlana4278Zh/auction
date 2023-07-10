package ru.skypro.auction.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skypro.auction.dto.*;
import ru.skypro.auction.entity.Bid;
import ru.skypro.auction.entity.Lot;
import ru.skypro.auction.exception.LotNotFoundException;
import ru.skypro.auction.exception.LotStatusNotStartedException;
import ru.skypro.auction.mapper.LotMapper;
import ru.skypro.auction.repository.BidRepository;
import ru.skypro.auction.repository.LotRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService{
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotMapper lotMapper;

    public BidDTO getFirstBidder(int id){
        return bidRepository.findFirstByLot_IdOrderByDateTimeAsc(id)
                .map(lotMapper::toBidDTO)
                .orElseThrow(LotNotFoundException::new);
    }

    public BidDTO getMostFrequentBidder(int id){
        return bidRepository.findTheMostFrequentBidder(id)
                .orElseThrow(LotNotFoundException::new);
    }

    public FullLot getFullLot(int id){
        FullLot fullLot = lotRepository.getFullLot(id)
                .orElseThrow(LotNotFoundException::new);
        fullLot.setCurrentPrice(lotRepository.getCurrentPrice(id));
        fullLot.setLastBid(bidRepository.findFirstByLot_IdOrderByDateTimeDesc(id)
                .map(lotMapper::toBidDTO)
                .orElseThrow(LotNotFoundException::new));
        return fullLot;
    }

    public void start(int id){
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
    }

    public void createBid(int id, BidDTO bidDTO){
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        if(lot.getStatus() == Status.CREATED || lot.getStatus() == Status.STOPPED){
            throw new LotStatusNotStartedException();
        }
        Bid bid = new Bid(bidDTO.getBidderName());
        bid.setLot(lot);
        bidRepository.save(bid);
    }

    public void stop(int id){
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
    }

    public LotDTO createLot(CreateLot newLot){
        return lotMapper.toLotDTO(lotRepository.save(lotMapper.toLot(newLot)));
    }

    public List<LotDTO> findLots(@Nullable Status status, int page){
        Pageable pageable = PageRequest.of(page, 10);
        return Optional.ofNullable(status)
                .map(s -> lotRepository.findAllByStatus(s, pageable))
                .orElseGet(() -> lotRepository.findAll(pageable)).stream()
                .map(lotMapper::toLotDTO)
                .toList()
        ;
    }

    public byte[] getCSVFile(){
        List<Lot> lots = lotRepository.findAll();
        StringWriter stringWriter = new StringWriter();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("id", "status", "title", "lastBid", "currentPrice")
                .build();
        try (CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat)){
            lots.forEach(lot -> {
                try {
                    csvPrinter.printRecord(lot.getId(),lot.getStatus(),lot.getTitle(), bidRepository.findFirstByLot_IdOrderByDateTimeDesc(lot.getId()).get(), lotRepository.getCurrentPrice(lot.getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
        return stringWriter.toString().getBytes(StandardCharsets.UTF_8);
    }
}
