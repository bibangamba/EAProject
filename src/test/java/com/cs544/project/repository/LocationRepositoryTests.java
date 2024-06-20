package com.cs544.project.repository;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.*;
import com.cs544.project.domain.Location;
import com.cs544.project.service.DatabaseInitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ReusableBeansTestConfiguration.class)
public class LocationRepositoryTests {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private LocationRepository locationRepository;
    @Test
    public void whenFindByLocationType_returnLocations() {

        Location location = Location.getSampleData();

        testEntityManager.persist(location);
        testEntityManager.flush();

        Collection<Location> locations = locationRepository.findByLocationType(location.getLocationType());
        assertFalse(locations.isEmpty());
        assertEquals(1, locations.size());
    }
}
