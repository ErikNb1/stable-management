package de.kisters.stablemanagement.horse.model.entity;

import de.kisters.stablemanagement.util.Identifiable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Horse extends Identifiable {
    private String name;
    private String lifeNumber;
    private String coatColour;
}
