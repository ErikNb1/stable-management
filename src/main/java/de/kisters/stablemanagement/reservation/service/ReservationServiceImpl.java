package de.kisters.stablemanagement.reservation.service;

import de.kisters.stablemanagement.reservation.model.dto.CreateReservationDto;
import de.kisters.stablemanagement.reservation.model.dto.ReservationDto;
import de.kisters.stablemanagement.reservation.model.entity.Reservation;
import de.kisters.stablemanagement.reservation.model.mapper.ReservationMapper;
import de.kisters.stablemanagement.reservation.repository.ReservationRepository;
import de.kisters.stablemanagement.util.exception.NoSpaceInBuildingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;


    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ReservationDto createReservation(CreateReservationDto dto) {
        Reservation reservation = reservationMapper.mapCreationToEntity(dto);
        List<Reservation> reservations = reservationRepository.findReservationOnThisTimeAndBuilding(reservation.getReservedPlaced().getId(), reservation.getReservedFrom(), reservation.getReservedTo());
        if(reservations.isEmpty()) {
            return reservationMapper.mapToDto(reservationRepository.save(reservation));
        } else if (reservations.size() < reservation.getReservedPlaced().getType().getCapacity()) {
            return reservationMapper.mapToDto(reservationRepository.save(reservation));
        } else {
            throw new NoSpaceInBuildingException("Reservation can't placed in this time range and building");
        }
    }

    @Override
    public List<ReservationDto> getAllForTodayByStable(int stableId) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        return getReservationForTimeRange(stableId, todayMidnight, tomorrowMidnight);
    }

    @Override
    public List<ReservationDto> getAllForWeekByStable(int stableId) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime MidnightInAWeek = todayMidnight.plusWeeks(1);
        return getReservationForTimeRange(stableId, todayMidnight, MidnightInAWeek);
    }

    @Override
    public List<ReservationDto> getAllForMonthByStable(int stableId) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime MidnightInAMonth = todayMidnight.plusMonths(1);
        return getReservationForTimeRange(stableId, todayMidnight, MidnightInAMonth);
    }

    private List<ReservationDto> getReservationForTimeRange(int stableId, LocalDateTime from,LocalDateTime to) {
        List<ReservationDto> temp = new ArrayList<>();
        List<Reservation> allBetween = reservationRepository.getAllBetween(stableId, from, to);
        allBetween.forEach(r -> temp.add(reservationMapper.mapToDto(r)));
        return temp;
    }
}
