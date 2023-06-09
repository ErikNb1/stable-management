package de.kisters.stablemanagement.stable.model.dto;

import de.kisters.stablemanagement.util.Identifiable;
import de.kisters.stablemanagement.stable.model.entity.BuildingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingDto extends Identifiable {
    private Integer id;
    private String name;
    private BuildingType type;
}
