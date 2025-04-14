package org.example.sachi.service.Impl;

import org.example.sachi.dto.HospitalDTO;
import org.example.sachi.entity.Hospital;
import org.example.sachi.repo.HospitalRepo;
import org.example.sachi.service.HospitalService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveHospital(HospitalDTO hospitalDTO) {
        if (hospitalRepo.existsById(hospitalDTO.getHospitalID())){
            throw new RuntimeException("HospitalID allready exit");
        }else {
            hospitalRepo.save(modelMapper.map(hospitalDTO, Hospital.class));
        }

    }

    @Override
    public void updateHospital(HospitalDTO hospitalDTO) {
        if(hospitalRepo.existsById(hospitalDTO.getHospitalID())){
            hospitalRepo.save(modelMapper.map(hospitalDTO, Hospital.class));
        }else{
            throw new RuntimeException("Hospital doesn't exit");
        }

    }

    @Override
    public void deleteHospital(Integer id) {
        if(hospitalRepo.existsById(id)){
            hospitalRepo.deleteById(id);
        }else {
            throw new RuntimeException("HospitalID doesn't exit");
        }
    }

    @Override
    public List<HospitalDTO> getAllHospital() {
        return modelMapper.map(hospitalRepo.findAll(),new TypeToken<List<Hospital>>(){}.getType());
    }

    @Override
    public List<Map<String, Object>> getAllHospitalID() {
        List<Object[]> allHosIds = hospitalRepo.findAllBloodBankIdsAndGroups();

        List<Map<String,Object>> hosDTa = new ArrayList<>();

        for (Object[] row : allHosIds){
            Map<String ,Object> map = new HashMap<>();
            map.put("hospitalID" ,row[0]);
            map.put("hospitalName", row[1]);
            hosDTa.add(map);
        }
        return hosDTa;
    }


}
