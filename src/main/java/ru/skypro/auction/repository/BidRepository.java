package ru.skypro.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.auction.dto.BidDTO;
import ru.skypro.auction.dto.FullLot;
import ru.skypro.auction.entity.Bid;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findFirstByLot_IdOrderByDateTimeAsc(int lotId);

    @Query("""
            SELECT new ru.skypro.auction.dto.BidDTO(b.name, b.dateTime) FROM Bid b WHERE b.name = (
            SELECT b.name FROM Bid b GROUP BY b.name ORDER BY count(b.name) DESC LIMIT 1) 
            ORDER BY b.dateTime DESC LIMIT 1
            """)
    Optional<BidDTO> findTheMostFrequentBidder(@Param("lotId") int lotId);
}
