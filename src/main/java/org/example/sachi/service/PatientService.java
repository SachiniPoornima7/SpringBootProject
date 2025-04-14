package org.example.sachi.service;

import org.example.sachi.dto.HospitalDTO;
import org.example.sachi.dto.PatientDTO;

import java.util.List;
import java.util.Map;

public interface PatientService {
    void savePatient(PatientDTO patientDTO);

    void updatePatient(PatientDTO patientDTO);

    void deletePatient(Integer id);

    List<PatientDTO> getAllPatient();

    List<Map<String, Object>> getAllDoctorlID();

    void updatePatienttatus(Integer id);

    void updatePatienttatusReject(Integer id);
}
