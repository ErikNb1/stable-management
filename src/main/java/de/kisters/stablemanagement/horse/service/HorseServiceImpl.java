package de.kisters.stablemanagement.horse.service;

import de.kisters.stablemanagement.horse.model.dto.HorseAddingDto;
import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.horse.model.entity.Horse;
import de.kisters.stablemanagement.horse.repository.HorseRepository;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.user.model.mapper.HorseMapper;
import de.kisters.stablemanagement.user.service.UserService;
import de.kisters.stablemanagement.util.exception.exception.NoEntityFoundException;
import org.springframework.stereotype.Service;

@Service
public class HorseServiceImpl implements HorseService {
    private final UserService userService;
    private final HorseMapper horseMapper;
    private final HorseRepository horseRepository;

    public HorseServiceImpl(UserService userService, HorseMapper horseMapper, HorseRepository horseRepository) {
        this.userService = userService;
        this.horseMapper = horseMapper;
        this.horseRepository = horseRepository;
    }

    @Override
    public HorseDto addHorse(HorseDto horseDto) {
        User user = userService.getUserEntityForToken();
        Horse horse = horseRepository.save(horseMapper.mapToEntity(horseDto));
        user.addHorse(horse);
        userService.save(user);
        return horseMapper.mapToDto(horse);
    }

    @Override
    public void addHorse(HorseAddingDto dto) {
        User user = userService.getUserById(dto.getUserId());
        Horse horse = horseRepository.findById(dto.getHorseId()).orElseThrow(() -> new NoEntityFoundException("No Horse found for ID " + dto.getHorseId()));
        user.addHorse(horse);
        userService.save(user);
    }
}
