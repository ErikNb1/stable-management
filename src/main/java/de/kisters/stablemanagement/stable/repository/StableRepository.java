package de.kisters.stablemanagement.stable.repository;

import de.kisters.stablemanagement.stable.model.entity.Stable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StableRepository extends JpaRepository<Stable, Integer> {
    @Query("SELECT s FROM Stable s LEFT JOIN s.buildings b WHERE b.id = :id")
    Optional<Stable> findStableByBuildingId(Integer id);
}
