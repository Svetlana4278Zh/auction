package ru.skypro.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullLot {
    private int id;

    private Status status;

    private String title;

    private String description;

    private int startPrice;

    private int bidPrice;

    private  int currentPrice;

    private BidDTO lastBid;


    public FullLot(int id, Status status, String title, String description, int startPrice, int bidPrice) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
    }
}
