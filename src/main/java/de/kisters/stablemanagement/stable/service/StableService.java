package de.kisters.stablemanagement.stable.service;

import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.stable.model.dto.BuildingDto;
import de.kisters.stablemanagement.stable.model.dto.StableDto;
import de.kisters.stablemanagement.stable.model.entity.Building;
import de.kisters.stablemanagement.user.model.dto.LoginDto;

public interface StableService {
    StableDto createStable(StableDto stableDto);

    StableDto createBuilding(BuildingDto buildingDto, int stableId);

    StableDto getStable(int id);

    StableDto addMember(LoginDto loginDto, int stableId);

    StableDto kickMember(LoginDto loginDto, int stableId);

    Building getBuilding(int buildingId);

    BuildingDto getMappedBuildingsDto(Building reservedPlaced);
}
