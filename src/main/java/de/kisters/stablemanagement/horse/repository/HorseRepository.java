package de.kisters.stablemanagement.horse.repository;

import de.kisters.stablemanagement.horse.model.entity.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Integer> {
}
