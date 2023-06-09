package de.kisters.stablemanagement.reservation.service;

import de.kisters.stablemanagement.reservation.model.dto.CreateReservationDto;
import de.kisters.stablemanagement.reservation.model.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(CreateReservationDto dto);

    List<ReservationDto> getAllForTodayByStable(int stableId);

    List<ReservationDto> getAllForWeekByStable(int stableId);

    List<ReservationDto> getAllForMonthByStable(int stableId);
}
