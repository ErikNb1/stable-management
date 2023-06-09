package de.kisters.stablemanagement.user.model.mapper;

import de.kisters.stablemanagement.horse.model.dto.HorseDto;
import de.kisters.stablemanagement.horse.model.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HorseMapper {
    public List<HorseDto> mapToDtos(List<Horse> horses) {
        List<HorseDto> temp = new ArrayList<>();
        horses.forEach(horse -> {
            temp.add(mapToDto(horse));
        });
        return temp;
    }

    public List<Horse> mapToEntities(List<HorseDto> dtos) {
        List<Horse> temp = new ArrayList<>();
        dtos.forEach(dto -> {
            temp.add(mapToEntity(dto));
        });
        return temp;
    }
    public HorseDto mapToDto(Horse horse) {
        HorseDto dto = new HorseDto();
        dto.setId(horse.getId());
        dto.setLifeNumber(horse.getLifeNumber());
        dto.setName(horse.getName());
        dto.setCoatColour(horse.getCoatColour());
        return dto;
    }

    public Horse mapToEntity(HorseDto dto) {
        Horse horse = new Horse();
        horse.setLifeNumber(dto.getLifeNumber());
        horse.setName(dto.getName());
        horse.setCoatColour(dto.getCoatColour());
        return horse;
    }
}
