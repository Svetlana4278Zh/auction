package ru.skypro.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skypro.auction.dto.BidDTO;
import ru.skypro.auction.dto.FullLot;
import ru.skypro.auction.dto.Status;
import ru.skypro.auction.entity.Bid;
import ru.skypro.auction.entity.Lot;
import ru.skypro.auction.exception.LotNotFoundException;
import ru.skypro.auction.exception.LotStatusNotStartedException;
import ru.skypro.auction.mapper.LotMapper;
import ru.skypro.auction.repository.BidRepository;
import ru.skypro.auction.repository.LotRepository;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService{
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotMapper lotMapper;

    public BidDTO getFirstBidder(int id){
        return bidRepository.findFirstByLot_IdOrderByDateTimeAsc(id)
                .map(lotMapper::toDTO)
                .orElseThrow(LotNotFoundException::new);
    }

    public BidDTO getMostFrequentBidder(int id){
        return bidRepository.findTheMostFrequentBidder(id)
                .orElseThrow(LotNotFoundException::new);
    }

    public FullLot getFullLot(int id){
        return lotRepository.getFullLot(id)
                .orElseThrow(LotNotFoundException::new);
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
        bidRepository.save(bid);
    }

}
