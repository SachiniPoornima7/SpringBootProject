package org.example.sachi.controller;


import org.example.sachi.dto.PatientDTO;
import org.example.sachi.service.PatientService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;


  @PostMapping("save")
    private ResponseUtil savePatient(@RequestBody PatientDTO patientDTO){
        patientService.savePatient(patientDTO);
        return new ResponseUtil(200, "Patient Save", null);
    }

    @PutMapping("update")
    private ResponseUtil updatePatient(@RequestBody PatientDTO patientDTO){
        patientService.updatePatient(patientDTO);
        return new ResponseUtil(201, "Patient Update", null);
    }

    @PutMapping("updateStatus/{id}")
    private ResponseUtil updatePatientStatus(@PathVariable Integer id){
        patientService.updatePatienttatus(id);
        return new ResponseUtil(201, "Patient status Update", null);
    }

    @PutMapping("updateStatusReject/{id}")
    private ResponseUtil updatePatientStatusReject(@PathVariable Integer id){
        patientService.updatePatienttatusReject(id);
        return new ResponseUtil(201, "Patient status Update", null);
    }


    @DeleteMapping("delete/{id}")
     private ResponseUtil deletePatient(@PathVariable Integer id){
         patientService.deletePatient(id);
         return new ResponseUtil(201, "Patient Delete", null);
     }

    @GetMapping("getAll")
    private ResponseUtil loadHospital(){
        return new ResponseUtil(201, "Load the Patient Data", patientService.getAllPatient());
    }
    @GetMapping("getAllIds")
    private ResponseUtil loadHospitalIds(){
        List<Map<String, Object>> allHospitalID = patientService.getAllDoctorlID();
        return new ResponseUtil(201, "Load the Hospital ids Data",allHospitalID );
    }

}
