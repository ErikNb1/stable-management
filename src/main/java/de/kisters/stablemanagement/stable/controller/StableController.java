package de.kisters.stablemanagement.stable.controller;

import de.kisters.stablemanagement.stable.model.dto.BuildingDto;
import de.kisters.stablemanagement.stable.model.dto.StableDto;
import de.kisters.stablemanagement.stable.service.StableService;
import de.kisters.stablemanagement.user.model.dto.LoginDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class StableController {
    private final StableService stableService;

    public StableController(StableService stableService) {
        this.stableService = stableService;
    }

    @GetMapping("/stable/{id}")
    public StableDto getStable(@PathVariable int id) {
        return stableService.getStable(id);
    }
    @PostMapping("/stable/new")
    public StableDto newHorse(@RequestBody @Valid final StableDto stableDto) {
        return stableService.createStable(stableDto);
    }
    @PostMapping("/stable/building/{id}")
    public StableDto newHorse(@RequestBody @Valid final BuildingDto buildingDto, @PathVariable int id) {
        return stableService.createBuilding(buildingDto, id);
    }
    @PostMapping("/stable/member/new/{stableId}")
    public StableDto newMember(@RequestBody @Valid final LoginDto loginDto, @PathVariable int stableId) {
        return stableService.addMember(loginDto, stableId);
    }
    @PutMapping("/stable/member/kick/{stableId}")
    public StableDto kickMember(@RequestBody @Valid final LoginDto loginDto, @PathVariable int stableId) {
        return stableService.kickMember(loginDto, stableId);
    }
}
