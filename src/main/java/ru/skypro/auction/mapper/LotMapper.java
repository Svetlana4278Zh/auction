package ru.skypro.auction.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.auction.dto.BidDTO;

@Component
public class LotMapper {
    public BidDTO toDTO(ru.skypro.auction.entity.Bid bid){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bid.getName());
        bidDTO.setBidDate(bid.getDateTime());
        return bidDTO;
    }
}
