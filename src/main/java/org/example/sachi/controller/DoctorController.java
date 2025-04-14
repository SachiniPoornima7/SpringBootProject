package org.example.sachi.controller;

import org.example.sachi.dto.DoctorDTO;
import org.example.sachi.service.DoctorService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("save")
    private ResponseUtil saveDoctor(@RequestBody DoctorDTO doctorDTO){
        doctorService.saveDoctor(doctorDTO);
        return new ResponseUtil(200, "Doctor Save", null);
    }

    @PutMapping("update")
    private ResponseUtil updateDoctor(@RequestBody DoctorDTO doctorDTO){
        doctorService.updateDoctor(doctorDTO);
        return new ResponseUtil(201, "Doctor Update", null);
    }

    @DeleteMapping("delete/{id}")
    private ResponseUtil deleteDoctor(@PathVariable Integer id){
        doctorService.deleteDoctor(id);
        return new ResponseUtil(201, "Doctor Delete", null);
    }

       @GetMapping("getAll")
        private ResponseUtil loadDoctor(){
        return new ResponseUtil(201, "Load the Doctor Data", doctorService.getAllDoctor());
       }
}
