package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.AvailableDateRequest;
import com.veterinary.dto.response.AvailableDateResponse;
import com.veterinary.entities.AvailableDate;

public interface AvailableDateMapper {

    AvailableDateResponse toResponse(AvailableDate date);

    AvailableDate toEntity(AvailableDateRequest request);
}
