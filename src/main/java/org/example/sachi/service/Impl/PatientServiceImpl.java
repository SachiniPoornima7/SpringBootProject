package org.example.sachi.service.Impl;

import org.example.sachi.dto.PatientDTO;
import org.example.sachi.entity.Doctor;
import org.example.sachi.entity.Patient;;
import org.example.sachi.repo.DoctorRepo;
import org.example.sachi.repo.PatientRepo;
import org.example.sachi.service.PatientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DoctorRepo doctorRepo;

    @Override
    public void savePatient(PatientDTO patientDTO) {
        // Check if the patient ID already exists
        if (patientRepo.existsById(patientDTO.getPatientID())) {
            throw new RuntimeException("Patient ID already exists");
        }

        // Convert DTO to Entity
        Patient patient = modelMapper.map(patientDTO, Patient.class);

        // Ensure that the doctor association is handled correctly
        if (patientDTO.getDoctorID() != 0) {
            // Verify if the doctor exists in the database
            Doctor doctor = doctorRepo.findById(patientDTO.getDoctorID())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            patient.setDoctorID(doctor); // Assign the doctor to the patient
        }

        // Save the patient entity
        patientRepo.save(patient);
    }

    @Override
    public void updatePatient(PatientDTO patientDTO) {
        if(patientRepo.existsById(patientDTO.getPatientID())){
            patientRepo.save(modelMapper.map(patientDTO, Patient.class));
        }else{
            throw new RuntimeException("Patient doesn't exit");
        }
    }

    @Override
    public void deletePatient(Integer id) {
        if(patientRepo.existsById(id)){
            patientRepo.deleteById(id);
        }else {
            throw new RuntimeException("PatientID doesn't exit");
        }
    }

    @Override
    public List<PatientDTO> getAllPatient() {
        return modelMapper.map(patientRepo.findAll(),new TypeToken<List<Patient>>(){}.getType());
    }

    @Override
    public List<Map<String, Object>> getAllDoctorlID() {
        List<Object[]> alldocIds = doctorRepo.findAllDoctorIds();

        List<Map<String,Object>> docdata = new ArrayList<>();

        for (Object[] row : alldocIds){
            Map<String ,Object> map = new HashMap<>();
            map.put("doctorID" ,row[0]);
            map.put("name", row[1]);
            docdata.add(map);
        }
        return docdata;
    }

    @Override
    public void updatePatienttatus(Integer id) {
        Optional<Patient> byId = patientRepo.findById(id);

        if (byId.isPresent()){
            Patient patient = byId.get();
            patient.setStatus("Approved");
            patientRepo.save(patient); //
        }else {
            throw new RuntimeException("patient id not found");
        }
    }

    @Override
    public void updatePatienttatusReject(Integer id) {
        Optional<Patient> byId = patientRepo.findById(id);

        if (byId.isPresent()){
            Patient patient = byId.get();
            patient.setStatus("Rejected");
            patientRepo.save(patient); //
        }else {
            throw new RuntimeException("patient id not found");
        }
    }

}
