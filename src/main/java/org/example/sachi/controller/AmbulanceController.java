package org.example.sachi.controller;

import org.example.sachi.dto.AmbulanceDTO;
import org.example.sachi.service.AmbulanceService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ambulance")
@CrossOrigin(origins = "*")
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @PostMapping("save")
    private ResponseUtil saveAmbulance(@RequestBody AmbulanceDTO ambulanceDTO){
        ambulanceService.saveAmbulance(ambulanceDTO);
        return new ResponseUtil(200, "Ambulance Save", null);
    }

    @PutMapping("update")
    private ResponseUtil updateAmbulance(@RequestBody AmbulanceDTO ambulanceDTO){
      ambulanceService.updateAmbulance(ambulanceDTO);
        return new ResponseUtil(201, "Ambulance Update", null);
    }

    @DeleteMapping("delete/{id}")
    private ResponseUtil deleteAmbulance(@PathVariable Integer id){
        ambulanceService.deleteAmbulance(id);
        return new ResponseUtil(201, "Ambulance Delete", null);
    }

   @GetMapping("getAll")
    private ResponseUtil loadAmbulance(){
        return new ResponseUtil(201, "Load the Ambulance Data", ambulanceService.getAllAmbulance());
    }


}
