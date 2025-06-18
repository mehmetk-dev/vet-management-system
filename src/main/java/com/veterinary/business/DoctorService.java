package com.veterinary.business;

import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.DoctorMapper;
import com.veterinary.dao.DoctorRepo;
import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        if (doctorRepo.existsByEmail(doctor.getEmail())) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.EMAIL_EXISTS,doctor.getEmail()));
        }

        if (doctorRepo.existsByPhone(doctor.getPhone())) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.PHONE_EXISTS,doctor.getPhone()));
        }

        return doctorMapper.toResponse(doctorRepo.save(doctor));
    }

    public DoctorResponse getResponse(long id){
        return doctorMapper.toResponse(doctorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.DOCTOR_NOT_FOUND,id))));
    }

    public Doctor getById(long id){
        return doctorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.DOCTOR_NOT_FOUND,id)));
    }

    public void delete(long id){
        this.doctorRepo.delete(this.getById(id));
    }

    @Transactional
    public DoctorResponse update(long id, DoctorRequest doctorRequest) {
        Doctor exists = this.getById(id);

        Optional<Doctor> byEmail = doctorRepo.findByEmail(doctorRequest.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            throw new EntityAlreadyExistsException("E-posta başka bir doktora ait.");
        }

        Optional<Doctor> byPhone = doctorRepo.findByPhone(doctorRequest.getPhone());
        if (byPhone.isPresent() && byPhone.get().getId() != id) {
            throw new EntityAlreadyExistsException("Telefon numarası başka bir doktora ait.");
        }

        doctorMapper.updateEntityFromRequest(exists, doctorRequest);
        return doctorMapper.toResponse(doctorRepo.save(exists));
    }

    public Page<Doctor> getAllDoctors(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return doctorRepo.findAll(pageable);
    }


}
