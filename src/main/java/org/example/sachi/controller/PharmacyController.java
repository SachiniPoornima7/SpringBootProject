package org.example.sachi.controller;

import org.example.sachi.dto.PharmacyDTO;
import org.example.sachi.service.PharmacyService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pharmacy")
@CrossOrigin(origins = "*")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping("save")
    private ResponseUtil savePharmacy(@RequestBody PharmacyDTO pharmacyDTO){
        pharmacyService.savePharmacy(pharmacyDTO);
        return new ResponseUtil(200, "Pharmacy Save",null);
    }

    @PutMapping("update")
    private ResponseUtil updatePramacy(@RequestBody PharmacyDTO pharmacyDTO){
        pharmacyService.updatePramacy(pharmacyDTO);
        return new ResponseUtil(201, "Pharmacy Update", null);
    }

    @DeleteMapping("delete/{id}")
    private ResponseUtil deletePharmacy(@PathVariable Integer id ){
        pharmacyService.deletePharmacy(id);
        return new ResponseUtil(201, "Pharmacy Delete", null);
    }

    @GetMapping("getAll")
    private ResponseUtil loadPharmacy(){
        return new ResponseUtil(201, "Load the Pharmacy Data", pharmacyService.getAllPharmacy());
    }
}
