package de.kisters.stablemanagement.stable.model.mapper;

import de.kisters.stablemanagement.stable.model.dto.BuildingDto;
import de.kisters.stablemanagement.stable.model.entity.Building;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingMapper {
    public List<Building> mapToEntities(List<BuildingDto> buildings) {
        List<Building> temp = new ArrayList<>();
        buildings.forEach(buildingDto -> temp.add(mapToEntity(buildingDto)));
        return temp;
    }

    public Building mapToEntity(BuildingDto dto) {
        Building building = new Building();
        if (dto.getId() != null) {
            building.setId(dto.getId());
        }
        building.setName(dto.getName());
        building.setType(dto.getType());
        return building;
    }

    public List<BuildingDto> mapToDtos(List<Building> dtos) {
        List<BuildingDto> temp = new ArrayList<>();
        dtos.forEach(building -> temp.add(mapToDto(building)));
        return temp;
    }

    public BuildingDto mapToDto(Building building) {
        BuildingDto dto = new BuildingDto();
        dto.setId(building.getId());
        dto.setName(building.getName());
        dto.setType(building.getType());
        return dto;
    }
}
