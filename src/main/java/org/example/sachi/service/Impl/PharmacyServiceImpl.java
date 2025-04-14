package org.example.sachi.service.Impl;

import org.example.sachi.dto.PharmacyDTO;
import org.example.sachi.entity.Pharmacy;
import org.example.sachi.repo.PharmacyRepo;
import org.example.sachi.service.PharmacyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    private PharmacyRepo pharmacyRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void savePharmacy(PharmacyDTO pharmacyDTO) {
        if (pharmacyRepo.existsById(pharmacyDTO.getPharmacyID())){
            throw new RuntimeException("Pharmacy allready exit");
        }else {
            pharmacyRepo.save(modelMapper.map(pharmacyDTO, Pharmacy.class));
        }
    }

    @Override
    public void updatePramacy(PharmacyDTO pharmacyDTO) {
        if(pharmacyRepo.existsById(pharmacyDTO.getPharmacyID())){
            pharmacyRepo.save(modelMapper.map(pharmacyDTO, Pharmacy.class));
        }else{
            throw new RuntimeException("Pharmacy doesn't exit");
        }
    }

    @Override
    public void deletePharmacy(Integer id) {
        if(pharmacyRepo.existsById(id)){
            pharmacyRepo.deleteById(id);
        }else {
            throw new RuntimeException("PharmacyID doesn't exit");
        }
    }

    @Override
    public List<PharmacyDTO> getAllPharmacy() {
        return modelMapper.map(pharmacyRepo.findAll(),new TypeToken<List<Pharmacy>>(){}.getType());
    }
}
