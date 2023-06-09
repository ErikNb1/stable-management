package de.kisters.stablemanagement.horse.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorseAddingDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer horseId;
}
