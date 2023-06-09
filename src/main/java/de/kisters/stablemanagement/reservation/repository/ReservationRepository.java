package de.kisters.stablemanagement.reservation.repository;

import de.kisters.stablemanagement.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.reservedFrom BETWEEN :from AND :to AND r.reservedPlaced.id = :stableId")
    List<Reservation> getAllBetween(int stableId, LocalDateTime from, LocalDateTime to);
}
