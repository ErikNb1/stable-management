package de.kisters.stablemanagement.user.model.dto;

import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.horse.model.entity.Horse;
import de.kisters.stablemanagement.user.model.entity.Role;
import de.kisters.stablemanagement.util.validation.registration.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String userName;
    private String email;
    private Role role;
    private List<HorseDto> horses = new ArrayList<>();

}
