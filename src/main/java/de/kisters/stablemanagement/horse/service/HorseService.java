package de.kisters.stablemanagement.horse.service;

import de.kisters.stablemanagement.horse.model.dto.HorseAddingDto;
import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.util.security.jwt.dto.JwtTokenDto;

public interface HorseService {
    HorseDto addHorse(HorseDto horseDto);
    void addHorse(HorseAddingDto dto);
}
