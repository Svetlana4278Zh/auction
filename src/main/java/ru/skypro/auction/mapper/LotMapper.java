package ru.skypro.auction.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.auction.dto.BidDTO;
import ru.skypro.auction.dto.CreateLot;
import ru.skypro.auction.dto.LotDTO;
import ru.skypro.auction.dto.Status;
import ru.skypro.auction.entity.Bid;
import ru.skypro.auction.entity.Lot;

@Component
public class LotMapper {
    public BidDTO toBidDTO(Bid bid){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bid.getName());
        bidDTO.setBidDate(bid.getDateTime());
        return bidDTO;
    }

    public Lot toLot(CreateLot createLot){
        Lot lot = new Lot(Status.CREATED,
                createLot.getTitle(),
                createLot.getDescription(),
                createLot.getStartPrice(),
                createLot.getBidPrice());
        return lot;
    }

    public LotDTO toLotDTO(Lot lot){
        LotDTO lotDTO = new LotDTO(lot.getId(),
                lot.getStatus(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getStartPrice(),
                lot.getBidPrice());
        return lotDTO;
    }
}
