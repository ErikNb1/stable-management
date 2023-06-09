package de.kisters.stablemanagement.user.model.entity;

import de.kisters.stablemanagement.util.Identifiable;
import de.kisters.stablemanagement.horse.model.entity.Horse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSON")
public class User extends Identifiable {

    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @ManyToMany
    @JoinTable(name = "PERSON_HORSES")
    private List<Horse> horses;
    @Enumerated(EnumType.STRING)
    private Role role;


    public void addHorse(Horse horse) {
        this.horses.add(horse);
    }
}
