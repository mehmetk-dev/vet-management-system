package com.veterinary.business;

import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.DoctorMapper;
import com.veterinary.dao.DoctorRepo;
import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepo doctorRepo;

    public DoctorService(DoctorMapper doctorMapper, DoctorRepo doctorRepo) {
        this.doctorMapper = doctorMapper;
        this.doctorRepo = doctorRepo;
    }

    public DoctorResponse save(DoctorRequest doctorRequest){
        Doctor doctor = this.doctorMapper.toEntity(doctorRequest);
        return doctorMapper.toResponse(doctorRepo.save(doctor));
    }

    public DoctorResponse getResponse(long id){
        return doctorMapper.toResponse(doctorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND)));
    }

    public Doctor getById(long id){
        return doctorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND));
    }

    public void delete(long id){
        this.doctorRepo.delete(this.getById(id));
    }

    public DoctorResponse update(long id,DoctorRequest doctorRequest){

        this.getById(id);

        Doctor updated = doctorMapper.toEntity(doctorRequest);
        updated.setId(id);

        return doctorMapper.toResponse(updated);
    }

    public Page<Doctor> getAllDoctors(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return doctorRepo.findAll(pageable);
    }


}
