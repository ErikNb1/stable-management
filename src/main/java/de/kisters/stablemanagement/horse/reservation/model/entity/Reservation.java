package de.kisters.stablemanagement.horse.reservation.model.entity;

import de.kisters.stablemanagement.stable.model.entity.Building;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.util.Identifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation extends Identifiable {
    private LocalDateTime reservedFrom, reservedTo;;
    @ManyToOne
    private Building reservedPlaced;
    @ManyToOne
    private User reservedBy;
    private String task;
}
