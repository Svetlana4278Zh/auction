package ru.skypro.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.auction.dto.FullLot;
import ru.skypro.auction.entity.Lot;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Integer> {

    @Query(value = """
            SELECT l.id,
                    l.status,
                    l.title,
                    l.description,
                    l.start_price,
                    l.bid_price,
                    l.start_price + l.bid_price * (SELECT count(b.id) FROM bid b WHERE b.lot_id = ?1) AS currentPrice,
                    q.name AS bidderName, 
                    q.date_time AS bidDate
            FROM lot l, 
                    (SELECT b.name, b.date_time FROM bid b WHERE b.lot_id = ?1 ORDER BY b.date_time DESC LIMIT 1) q
            WHERE l.id = ?1
            """, nativeQuery = true)
    Optional<FullLot> getFullLot(@Param("lotId") int lotId);
}
