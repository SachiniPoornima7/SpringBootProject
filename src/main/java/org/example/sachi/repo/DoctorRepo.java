package org.example.sachi.repo;

import org.example.sachi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d.doctorID ,d.name FROM Doctor d")
    List<Object[]> findAllDoctorIds();
}
