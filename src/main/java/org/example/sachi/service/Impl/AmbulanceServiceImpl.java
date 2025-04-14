package org.example.sachi.service.Impl;

import org.example.sachi.dto.AmbulanceDTO;
import org.example.sachi.entity.Ambulance;
import org.example.sachi.repo.AmbulanceRepo;
import org.example.sachi.service.AmbulanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbulanceServiceImpl implements AmbulanceService {

    @Autowired
    private AmbulanceRepo ambulanceRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveAmbulance(AmbulanceDTO ambulanceDTO) {
        if (ambulanceRepo.existsById(ambulanceDTO.getAmbulanceID())){
            throw new RuntimeException("AmbulanceID already exit");
        }else {
            ambulanceRepo.save(modelMapper.map(ambulanceDTO, Ambulance.class));
        }
    }

    @Override
    public void updateAmbulance(AmbulanceDTO ambulanceDTO) {
        if (ambulanceRepo.existsById(ambulanceDTO.getAmbulanceID())){
            ambulanceRepo.save(modelMapper.map(ambulanceDTO, Ambulance.class));
        }else {
            throw new RuntimeException("Ambulance doesn't exit");
        }
    }

    @Override
    public void deleteAmbulance(Integer id) {
        if (ambulanceRepo.existsById(id)){
            ambulanceRepo.deleteById(id);
        }else {
            throw new RuntimeException("AmbulanceID doesn't exit");
        }
    }

    @Override
    public List<AmbulanceDTO> getAllAmbulance() {
        return modelMapper.map(ambulanceRepo.findAll(),new TypeToken<List<Ambulance>>(){}.getType());
    }


}
