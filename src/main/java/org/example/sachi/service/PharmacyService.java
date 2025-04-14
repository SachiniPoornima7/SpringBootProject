package org.example.sachi.service;

import org.example.sachi.dto.PharmacyDTO;

import java.util.List;

public interface PharmacyService {
    void savePharmacy(PharmacyDTO pharmacyDTO);

    void updatePramacy(PharmacyDTO pharmacyDTO);

    void deletePharmacy(Integer id);

    List<PharmacyDTO> getAllPharmacy();

}
