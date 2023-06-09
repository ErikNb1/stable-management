package de.kisters.stablemanagement.util;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Identifiable {
    @Id
    @GeneratedValue
    private Integer id;
}
