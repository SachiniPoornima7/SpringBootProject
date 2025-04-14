package org.example.sachi.repo;

import org.example.sachi.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, Integer> {
}
