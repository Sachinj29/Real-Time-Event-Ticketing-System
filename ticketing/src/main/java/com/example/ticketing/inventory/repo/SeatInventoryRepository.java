package com.example.ticketing.inventory.repo;

import com.example.ticketing.inventory.domain.InventoryStatus;
import com.example.ticketing.inventory.domain.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {

    List<SeatInventory> findByEventIdAndSeatIdIn(Long eventId, Collection<Long> seatIds);

    @Modifying
    @Query("""
    update SeatInventory si
      set si.status = 'LOCKED', si.lockExpiresAt = :until
    where si.eventId = :eventId and si.seatId in :seatIds
      and (si.status = 'AVAILABLE' or si.lockExpiresAt < :now)
  """)
    int lockSeats(Long eventId, Collection<Long> seatIds, Instant now, Instant until);

    @Modifying
    @Query("""
    update SeatInventory si
      set si.status = 'AVAILABLE', si.lockExpiresAt = null
    where si.eventId = :eventId and si.seatId in :seatIds
      and si.status = 'LOCKED' and si.lockExpiresAt < :now
  """)
    int releaseExpiredLocks(Long eventId, Collection<Long> seatIds, Instant now);

    @Modifying
    @Query("""
    update SeatInventory si
      set si.status = 'AVAILABLE', si.lockExpiresAt = null
    where si.status = 'LOCKED' and si.lockExpiresAt < :now
  """)
    int releaseAllExpired(Instant now);

    @Modifying
    @Query("""
    update SeatInventory si
      set si.status = 'SOLD', si.lockExpiresAt = null
    where si.eventId = :eventId and si.seatId in :seatIds
      and si.status = 'LOCKED'
  """)
    int markSold(Long eventId, Collection<Long> seatIds);
}
