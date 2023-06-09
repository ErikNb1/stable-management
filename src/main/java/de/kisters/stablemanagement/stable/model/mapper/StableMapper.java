package de.kisters.stablemanagement.stable.model.mapper;

import de.kisters.stablemanagement.stable.model.dto.StableDto;
import de.kisters.stablemanagement.stable.model.entity.Stable;
import de.kisters.stablemanagement.user.model.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class StableMapper {
    private final UserMapper userMapper;
    private final BuildingMapper buildingMapper;

    public StableMapper(UserMapper userMapper, BuildingMapper buildingMapper) {
        this.userMapper = userMapper;
        this.buildingMapper = buildingMapper;
    }

    public Stable mapDtoToEntity(StableDto dto) {
        Stable stable = new Stable();
        stable.setName(dto.getName());
        stable.setMembers(userMapper.mapToEntities(dto.getMembers()));
        stable.setBuildings(buildingMapper.mapToEntities(dto.getBuildings()));
        return stable;
    }
    public StableDto mapEntityToDto(Stable stable) {
        StableDto dto = new StableDto();
        dto.setId(stable.getId());
        dto.setName(stable.getName());
        dto.setOwner(userMapper.mapUserToDto(stable.getOwner()));
        dto.setMembers(userMapper.mapToDtos(stable.getMembers()));
        dto.setBuildings(buildingMapper.mapToDtos(stable.getBuildings()));
        return dto;
    }
}
