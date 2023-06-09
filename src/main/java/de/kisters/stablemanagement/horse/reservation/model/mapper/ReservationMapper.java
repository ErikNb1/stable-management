package de.kisters.stablemanagement.horse.reservation.model.mapper;

import de.kisters.stablemanagement.horse.reservation.model.dto.CreateReservationDto;
import de.kisters.stablemanagement.horse.reservation.model.dto.ReservationDto;
import de.kisters.stablemanagement.horse.reservation.model.entity.Reservation;
import de.kisters.stablemanagement.stable.service.StableService;
import de.kisters.stablemanagement.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    private final UserService userService;
    private final StableService stableService;

    public ReservationMapper(UserService userService, StableService stableService) {
        this.userService = userService;
        this.stableService = stableService;
    }

    public ReservationDto mapToDto(Reservation entity) {
        ReservationDto dto = new ReservationDto();
        dto.setId(entity.getId());
        dto.setReservedFrom(entity.getReservedFrom());
        dto.setReservedTo(entity.getReservedTo());
        dto.setReservedBy(userService.getMappedDto(entity.getReservedBy()));
        dto.setReservedPlaced(stableService.getMappedBuildingsDto(entity.getReservedPlaced()));
        dto.setTask(entity.getTask());
        return dto;
    }

    public Reservation mapCreationToEntity(CreateReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setReservedFrom(dto.getReservedFrom());
        reservation.setReservedTo(dto.getReservedTo());
        reservation.setReservedBy(userService.getUserEntityForToken());
        reservation.setReservedPlaced(stableService.getBuilding(dto.getReservedPlaceId()));
        reservation.setTask(dto.getTask());
        return reservation;
    }
}
