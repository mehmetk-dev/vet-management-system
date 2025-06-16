package com.veterinary.business;

import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.VaccineMapper;
import com.veterinary.dao.VaccineRepo;
import com.veterinary.dto.request.VaccineRequest;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaccineService {

    private final VaccineMapper vaccineMapper;
    private final VaccineRepo vaccineRepo;

    public VaccineService(VaccineMapper vaccineMapper, VaccineRepo vaccineRepo) {
        this.vaccineMapper = vaccineMapper;
        this.vaccineRepo = vaccineRepo;
    }

    public VaccineResponse save(VaccineRequest vaccineRequest){
        return vaccineMapper.toResponse(vaccineRepo.save(vaccineMapper.toEntity(vaccineRequest)));
    }

    public VaccineResponse getResponse(long id){
        return vaccineMapper.toResponse(vaccineRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND)));
    }

    public Vaccine get(long id){
        return vaccineRepo.findById(id).orElseThrow(()-> new NotFoundException(ExceptionMessages.NOT_FOUND));
    }

    public void delete(long id){
        this.vaccineRepo.delete(this.get(id));
    }

    public VaccineResponse update(long id,VaccineRequest vaccineRequest){

        Vaccine existing = this.get(id);

        vaccineMapper.updateEntityFromRequest(existing, vaccineRequest);
        return vaccineMapper.toResponse(vaccineRepo.save(existing));
    }

    public Page<Vaccine> getAllVaccines(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return vaccineRepo.findAll(pageable);
    }
}
