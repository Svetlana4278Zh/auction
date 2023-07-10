package ru.skypro.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.auction.dto.FullLot;
import ru.skypro.auction.dto.Status;
import ru.skypro.auction.entity.Lot;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Integer> {

    @Query(value = """
            SELECT new ru.skypro.auction.dto.FullLot(l.id, 
                    l.status,
                    l.title,
                    l.description,
                    l.startPrice,
                    l.bidPrice
                    ) 
            FROM Lot l
            WHERE l.id = :lotId
            """)
    Optional<FullLot> getFullLot(@Param("lotId") int lotId);

    @Query(value = """
            SELECT l.start_price + l.bid_price * (SELECT count(b.id) FROM bid b WHERE b.lot_id = :lotId) 
            FROM lot l
            WHERE l.id = :lotId
            """, nativeQuery = true)
    int getCurrentPrice(@Param("lotId") int lotId);

    Page<Lot> findAllByStatus(Status status, Pageable pageable);
}