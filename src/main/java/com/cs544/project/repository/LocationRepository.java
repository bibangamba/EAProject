package com.cs544.project.repository;

import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Collection<Location> findByLocationType(LocationType locationType);
}
