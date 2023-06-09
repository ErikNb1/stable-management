package de.kisters.stablemanagement.reservation.model.dto;

import de.kisters.stablemanagement.stable.model.dto.BuildingDto;
import de.kisters.stablemanagement.user.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {
    private Integer id;
    private UserDto reservedBy;
    private BuildingDto reservedPlaced;
    private String task;
    private LocalDateTime reservedFrom, reservedTo;;
}
