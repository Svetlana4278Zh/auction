package ru.skypro.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name =  "date_time", nullable = false, updatable = false)
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public Bid(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "name='" + name + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
