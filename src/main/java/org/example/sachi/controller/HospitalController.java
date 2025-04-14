package org.example.sachi.controller;

import org.example.sachi.dto.HospitalDTO;
import org.example.sachi.service.HospitalService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/hospital")
@CrossOrigin(origins = "*")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("save")
    private ResponseUtil saveHospital(@RequestBody HospitalDTO hospitalDTO){
        hospitalService.saveHospital(hospitalDTO);
        return new ResponseUtil(200, "Hospital Save", null);
    }

    @PutMapping("update")
    private ResponseUtil updateHospital(@RequestBody HospitalDTO hospitalDTO){
        hospitalService.updateHospital(hospitalDTO);
        return new ResponseUtil(201, "Hospital Update", null);
    }

    @DeleteMapping("delete/{id}")
    private ResponseUtil deleteHospital(@PathVariable Integer id){
        hospitalService.deleteHospital(id);
        return new ResponseUtil(201, "Hospital Delete", null);
    }

    @GetMapping("getAll")
    private ResponseUtil loadHospital(){
        return new ResponseUtil(201, "Load the Hospital Data", hospitalService.getAllHospital());
    }


    @GetMapping("getAllIds")
    private ResponseUtil loadHospitalIds(){
        List<Map<String, Object>> allHospitalID = hospitalService.getAllHospitalID();
        return new ResponseUtil(201, "Load the Hospital ids Data",allHospitalID );
    }

}
