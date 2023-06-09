package de.kisters.stablemanagement.stable.model.entity;

import de.kisters.stablemanagement.util.Identifiable;
import de.kisters.stablemanagement.user.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Stable extends Identifiable {

    @OneToOne
    private User owner;
    private String name;
    @OneToMany
    private List<Building> buildings;
    @ManyToMany
    private List<User> members;

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void addMember(User user) {
        this.members.add(user);
    }
}
