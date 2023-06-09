package de.kisters.stablemanagement.horse.controller;

import de.kisters.stablemanagement.horse.model.dto.HorseAddingDto;
import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.horse.service.HorseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class HorseController {
    private final HorseService horseService;

    public HorseController(HorseService horseService) {
        this.horseService = horseService;
    }

    @PostMapping("/horse/new")
    public HorseDto newHorse(@RequestBody @Valid final HorseDto horseDto) {
        return horseService.addHorse(horseDto);
    }
    @PutMapping("/horse/add")
    public void addExistingHorseToUser(@RequestBody @Valid HorseAddingDto dto) {
        horseService.addHorse(dto);
    }
}
