package de.kisters.stablemanagement.reservation.controller;

import de.kisters.stablemanagement.reservation.model.dto.CreateReservationDto;
import de.kisters.stablemanagement.reservation.model.dto.ReservationDto;
import de.kisters.stablemanagement.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation/new")
    public ReservationDto createReservationForSpecificBuilding(@RequestBody CreateReservationDto dto) {
        return reservationService.createReservation(dto);
    }
    @GetMapping("/reservation/today/{stableId}")
    public List<ReservationDto> getAllReservationsForToday(@PathVariable int stableId) {
        return reservationService.getAllForTodayByStable(stableId);
    }
    @GetMapping("/reservation/week/{stableId}")
    public List<ReservationDto> getAllReservationsForWeek(@PathVariable int stableId) {
        return reservationService.getAllForWeekByStable(stableId);
    }
    @GetMapping("/reservation/month/{stableId}")
    public List<ReservationDto> getAllReservationsForMonth(@PathVariable int stableId) {
        return reservationService.getAllForMonthByStable(stableId);
    }
}
