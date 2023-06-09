package de.kisters.stablemanagement.stable.model.dto;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class HallDto extends BuildingDto{
    private boolean full;
    private List<UserDto> placedBy;
}
