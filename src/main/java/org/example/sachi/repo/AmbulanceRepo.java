package org.example.sachi.repo;

import org.example.sachi.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbulanceRepo extends JpaRepository<Ambulance, Integer> {
}
