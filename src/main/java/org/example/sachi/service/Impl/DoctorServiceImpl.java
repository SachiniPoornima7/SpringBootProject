package org.example.sachi.service.Impl;

import org.example.sachi.dto.DoctorDTO;
import org.example.sachi.entity.Doctor;
import org.example.sachi.entity.Hospital;
import org.example.sachi.repo.DoctorRepo;
import org.example.sachi.repo.HospitalRepo;
import org.example.sachi.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HospitalRepo hospitalRepo;

    @Override
    public void saveDoctor(DoctorDTO doctorDTO) {
        // Check if a doctor with the given ID already exists
        if (doctorRepo.existsById(doctorDTO.getDoctorId())) {
            throw new RuntimeException("Doctor ID already exists");
        } else {
            // Convert the DoctorDTO to Doctor entity and save it
            Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

            // Ensure that the hospital association is handled correctly
            if (doctor.getHospitalID() != null && doctor.getHospitalID().getHospitalID() != 0) {
                // If hospital association is provided, make sure the hospital is managed (already persisted)
                Hospital hospital = hospitalRepo.findById(doctor.getHospitalID().getHospitalID())
                        .orElseThrow(() -> new RuntimeException("Hospital not found"));
                doctor.setHospitalID(hospital); // Ensure the hospital is set correctly
            }

            // Save the doctor entity
            doctorRepo.save(doctor);
        }
    }



    @Override
    public void updateDoctor(DoctorDTO doctorDTO) {
        // Check if doctor exists in the database
        if (!doctorRepo.existsById(doctorDTO.getDoctorId())) {
            throw new RuntimeException("Doctor ID " + doctorDTO.getDoctorId() + " doesn't exist in the database");
        }

        // Fetch the doctor entity from the database
        Doctor existingDoctor = doctorRepo.findById(doctorDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor ID " + doctorDTO.getDoctorId() + " not found"));

        // Map the DTO to entity, but don't overwrite the existing doctor ID (primary key)
        modelMapper.map(doctorDTO, existingDoctor);

        // Fetch hospital entity based on the hospital ID from the DTO
        Hospital hospital = hospitalRepo.findById(doctorDTO.getHospitalID())
                .orElseThrow(() -> new RuntimeException("Hospital ID " + doctorDTO.getHospitalID() + " not found"));

        // Assign the hospital object to the doctor
        existingDoctor.setHospitalID(hospital);

        // Save the updated doctor entity
        doctorRepo.save(existingDoctor);
    }



    @Override
    public void deleteDoctor(Integer id) {
        // Check if the doctor exists
        if (doctorRepo.existsById(id)) {
            // Fetch the doctor by ID
            Doctor doctor = doctorRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor with ID " + id + " not found"));

            // Get the associated hospital of the doctor
            Hospital hospital = doctor.getHospitalID();

            // Delete the doctor
            doctorRepo.deleteById(id);

            // After deleting the doctor, check if there are any remaining doctors in the hospital

        } else {
            throw new RuntimeException("DoctorID doesn't exist");
        }
    }



    @Override
       public List<DoctorDTO> getAllDoctor() {
         return modelMapper.map(doctorRepo.findAll(),new TypeToken<List<Doctor>>(){}.getType());
       }

}
