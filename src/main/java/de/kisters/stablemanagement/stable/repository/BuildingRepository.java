package de.kisters.stablemanagement.stable.repository;

import de.kisters.stablemanagement.stable.model.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
