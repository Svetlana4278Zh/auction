package ru.skypro.auction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.auction.dto.Status;

@Getter
@Setter
@Entity
@Table(name = "lot")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 7)
    private Status status;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(length = 4096,nullable = false)
    private String description;

    @Column(name = "start_price", nullable = false)
    private int startPrice;

    @Column(name = "bid_price",nullable = false)
    private int bidPrice;
}
