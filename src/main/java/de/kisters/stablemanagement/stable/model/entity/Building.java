package de.kisters.stablemanagement.stable.model.entity;

import de.kisters.stablemanagement.util.Identifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Building extends Identifiable {
    private String name;
    @Enumerated(EnumType.STRING)
    private BuildingType type;
}
