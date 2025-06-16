package com.veterinary.business;

import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AvailableDateMapper;
import com.veterinary.dao.AvailableDateRepo;
import com.veterinary.dto.request.AvailableDateRequest;
import com.veterinary.dto.response.AvailableDateResponse;
import com.veterinary.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final AvailableDateMapper availableDateMapper;

    public AvailableDateService(AvailableDateRepo availableDateRepo, AvailableDateMapper availableDateMapper) {
        this.availableDateRepo = availableDateRepo;
        this.availableDateMapper = availableDateMapper;
    }

    public AvailableDateResponse save(AvailableDateRequest request){
        AvailableDate date = this.availableDateMapper.toEntity(request);
        return availableDateMapper.toResponse(availableDateRepo.save(date));
    }

    public AvailableDateResponse getResponse(long id){
        return availableDateMapper.toResponse(availableDateRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND)));
    }

    public AvailableDate getById(long id){
        return availableDateRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND));
    }

    public void delete(long id){
        this.availableDateRepo.delete(this.getById(id));
    }

    public AvailableDateResponse update(long id,AvailableDateRequest request){

        this.getById(id);

        AvailableDate updated = availableDateMapper.toEntity(request);
        updated.setId(id);

        return availableDateMapper.toResponse(updated);
    }

    public Page<AvailableDate> getAllAvailableDates(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return availableDateRepo.findAll(pageable);
    }

}
