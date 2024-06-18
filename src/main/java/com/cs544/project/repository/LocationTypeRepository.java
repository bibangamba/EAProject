package com.cs544.project.repository;

import com.cs544.project.domain.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationTypeRepository extends JpaRepository<LocationType, Integer> {
}
