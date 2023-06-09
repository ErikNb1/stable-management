package de.kisters.stablemanagement.stable.model.dto;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundPenDto extends BuildingDto{
    private boolean placed;
    private UserDto placedBy;
}
