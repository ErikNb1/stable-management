package de.kisters.stablemanagement.stable.model.dto;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StableDto {

    private Integer id;
    private String name;
    private UserDto owner;
    private List<BuildingDto> buildings;
    private List<UserDto> members;
}
