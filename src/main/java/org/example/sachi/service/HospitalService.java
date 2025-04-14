package org.example.sachi.service;

import org.example.sachi.dto.HospitalDTO;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    void saveHospital(HospitalDTO hospitalDTO);

    void updateHospital(HospitalDTO hospitalDTO);

    void deleteHospital(Integer id);

    List<HospitalDTO> getAllHospital();

    List<Map<String,Object>> getAllHospitalID();
}
