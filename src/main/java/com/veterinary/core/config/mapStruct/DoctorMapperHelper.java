package com.veterinary.core.config.mapStruct;

import com.veterinary.entities.Appointment;
import com.veterinary.entities.AvailableDate;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorMapperHelper {

    @Named("mapAvailableDatesToLocalDate")
    public List<LocalDate> mapAvailableDatesToLocalDate(List<AvailableDate> dates) {
        if (dates == null) return Collections.emptyList();
        return dates.stream()
                .map(AvailableDate::getAvailable)
                .collect(Collectors.toList());
    }

    @Named("mapAppointmentsToLocalDateTime")
    public List<LocalDateTime> mapAppointmentsToLocalDateTime(List<Appointment> appointments) {
        if (appointments == null) return Collections.emptyList();
        return appointments.stream()
                .map(Appointment::getAppointmentDate)
                .collect(Collectors.toList());
    }
}
