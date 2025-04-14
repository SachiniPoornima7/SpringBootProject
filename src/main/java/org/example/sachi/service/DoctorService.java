package org.example.sachi.service;

import org.example.sachi.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {

    void saveDoctor(DoctorDTO doctorDTO);

    void updateDoctor(DoctorDTO doctorDTO);

    void deleteDoctor(Integer id);

    List<DoctorDTO> getAllDoctor();

}
