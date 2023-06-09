package de.kisters.stablemanagement.stable.service;

import de.kisters.stablemanagement.stable.model.dto.BuildingDto;
import de.kisters.stablemanagement.stable.model.dto.StableDto;
import de.kisters.stablemanagement.stable.model.entity.Building;
import de.kisters.stablemanagement.stable.model.entity.Stable;
import de.kisters.stablemanagement.stable.model.mapper.BuildingMapper;
import de.kisters.stablemanagement.stable.model.mapper.StableMapper;
import de.kisters.stablemanagement.stable.repository.BuildingRepository;
import de.kisters.stablemanagement.stable.repository.StableRepository;
import de.kisters.stablemanagement.user.model.dto.LoginDto;
import de.kisters.stablemanagement.user.model.entity.Role;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.user.service.UserService;
import de.kisters.stablemanagement.util.exception.UserNotPermittedException;
import de.kisters.stablemanagement.util.exception.exception.NoEntityFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StableServiceImpl implements StableService {
    private final StableRepository stableRepository;
    private final BuildingRepository buildingRepository;
    private final UserService userService;
    private final StableMapper stableMapper;
    private final BuildingMapper buildingMapper;

    public StableServiceImpl(StableRepository stableRepository, BuildingRepository buildingRepository, UserService userService, StableMapper stableMapper, BuildingMapper buildingMapper) {
        this.stableRepository = stableRepository;
        this.buildingRepository = buildingRepository;
        this.userService = userService;
        this.stableMapper = stableMapper;
        this.buildingMapper = buildingMapper;
    }

    @Override
    public StableDto createStable(StableDto stableDto) {
        User user = userIsPermitted();
        Stable stable = stableMapper.mapDtoToEntity(stableDto);
        stable.setOwner(user);
        return stableMapper.mapEntityToDto(stableRepository.save(stable));
    }

    @Override
    public StableDto createBuilding(BuildingDto buildingDto, int stableId) {
        userIsPermitted();
        Building saved = buildingRepository.save(buildingMapper.mapToEntity(buildingDto));
        Stable stable = stableRepository.findById(stableId).orElseThrow(() -> new EntityNotFoundException("No Stable found for ID " + stableId));
        stable.addBuilding(saved);
        return stableMapper.mapEntityToDto(stableRepository.save(stable));
    }

    @Override
    public StableDto getStable(int id) {
        return stableMapper.mapEntityToDto(stableRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Stable found for ID " + id)));
    }

    @Override
    public StableDto addMember(LoginDto loginDto, int stableId) {
        User user = userService.getUserByEmail(loginDto.getUserName());
        Stable stable = stableRepository.findById(stableId).orElseThrow(() -> new EntityNotFoundException("No Stable found for ID " + stableId));
        stable.addMember(user);
        return stableMapper.mapEntityToDto(stableRepository.save(stable));
    }

    @Override
    public StableDto kickMember(LoginDto loginDto, int stableId) {
        User user = userService.getUserByEmail(loginDto.getUserName());
        Stable stable = stableRepository.findById(stableId).orElseThrow(() -> new EntityNotFoundException("No Stable found for ID " + stableId));
        stable.getMembers().remove(user);
        return stableMapper.mapEntityToDto(stableRepository.save(stable));
    }

    @Override
    public Building getBuilding(int buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(() -> new NoEntityFoundException("No building found for Id" + buildingId));
        if (isUserPermittedToReserved(building)) {
            return building;
        } else {
            throw new UserNotPermittedException("User not permitted for the Reservation");
        }
    }

    @Override
    public BuildingDto getMappedBuildingsDto(Building building) {
        if (isUserPermittedToReserved(building))
            return buildingMapper.mapToDto(building);
        else
            return null;
    }

    private boolean isUserPermittedToReserved(Building building) {
        User current = userService.getUserEntityForToken();
        Stable stableByBuildingId = stableRepository.findStableByBuildingId(building.getId())
                .orElseThrow(() -> new EntityNotFoundException("No Stable found for Building " + building.getName() + "(" + building.getId() + ")"));
        return stableByBuildingId.getMembers().contains(current) || stableByBuildingId.getOwner().equals(current);
    }

    private User userIsPermitted() {
        User user = userService.getUserEntityForToken();
        if (!user.getRole().equals(Role.STABLE_OWNER)) {
            throw new UserNotPermittedException("This user not allowed to create Stable or Building");
        }
        return user;
    }
}
