package org.example.sachi.service;

import org.example.sachi.dto.AmbulanceDTO;

import java.util.List;

public interface AmbulanceService {
    void saveAmbulance(AmbulanceDTO ambulanceDTO);

    void updateAmbulance(AmbulanceDTO ambulanceDTO);

    void deleteAmbulance(Integer id);

    List<AmbulanceDTO> getAllAmbulance();

}
