package de.kisters.stablemanagement.user.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StableOwnerDto extends UserDto {
    private List<UserDto> horseOwners;
}
