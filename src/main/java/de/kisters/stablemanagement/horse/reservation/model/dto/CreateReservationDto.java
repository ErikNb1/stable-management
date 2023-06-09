package de.kisters.stablemanagement.horse.reservation.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateReservationDto {
    private LocalDateTime reservedFrom, reservedTo;
    @NotNull
    private Integer reservedPlaceId;
    private String task;

}
