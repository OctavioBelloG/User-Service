package com.example.mapper;

import com.example.model.Address;
import com.example.model.Patient;
import com.example.model.User;
import com.example.dto.PatientResponse;
import com.example.dto.PatientRequest;
import com.example.dto.AddressDto;
import java.sql.Date;

public final class PatientMapper {

    public static PatientResponse toResponse(Patient patient) {
        if (patient == null) return null;

        Date birth = null;
        if (patient.getBirthDate() != null) {
            birth = Date.valueOf(patient.getBirthDate());
        }

        // Mapeo manual de Entidad Address -> DTO Address
        AddressDto addressDto = null;
        if (patient.getAddress() != null) {
            addressDto = AddressDto.builder()
                    .street(patient.getAddress().getStreet())
                    .intNumber(patient.getAddress().getIntNumber())
                    .extNumber(patient.getAddress().getExtNumber())
                    .neighborhood(patient.getAddress().getNeighborhood())
                    .city(patient.getAddress().getCity())
                    .zipCode(patient.getAddress().getZipCode())
                    .build();
        }

        return PatientResponse.builder()
                .patientId(patient.getPatientId())
                .firstName(patient.getFirstName())
                .paternalSurname(patient.getPaternalSurname())
                .maternalSurname(patient.getMaternalSurname())
                .birthDate(birth)
                .address(addressDto) // Insertamos el objeto completo
                .phone(patient.getPhone())
                .userId(patient.getUser() != null ? patient.getUser().getUserId() : null)
                .userName(patient.getUser() != null ? patient.getUser().getUsername() : null)
                .build();
    }

    public static Patient toEntity(PatientRequest dto) {
        if (dto == null) return null;
        Patient p = new Patient();
        
        p.setFirstName(dto.getFirstName());
        p.setPaternalSurname(dto.getPaternalSurname());
        p.setMaternalSurname(dto.getMaternalSurname());

        if (dto.getBirthDate() != null) {
            p.setBirthDate(dto.getBirthDate().toLocalDate());
        }
        
        p.setPhone(dto.getPhone());

        // Mapeo de DTO Address -> Entidad Address
        // Se crea la instancia y se asignan relaciones bidireccionales si es necesario
        if (dto.getAddress() != null) {
            Address a = new Address();
            a.setStreet(dto.getAddress().getStreet());
            a.setIntNumber(dto.getAddress().getIntNumber());
            a.setExtNumber(dto.getAddress().getExtNumber());
            a.setNeighborhood(dto.getAddress().getNeighborhood());
            a.setCity(dto.getAddress().getCity());
            a.setZipCode(dto.getAddress().getZipCode());
            
            // IMPORTANTE: Relación 1 a 1
            p.setAddress(a); // El paciente es dueño de la llave foránea
            // Si tu entidad Address tiene el campo 'patient', descomenta la sig línea:
            // a.setPatient(p); 
        }

        if (dto.getUserId() != null) {
            User u = new User();
            u.setUserId(dto.getUserId());
            p.setUser(u);
        }
        return p;
    }

    public static void copyToEntity(PatientRequest dto, Patient entity) {
        if (dto == null || entity == null) return;
        
        entity.setFirstName(dto.getFirstName());
        entity.setPaternalSurname(dto.getPaternalSurname());
        entity.setMaternalSurname(dto.getMaternalSurname());

        if (dto.getBirthDate() != null) {
            entity.setBirthDate(dto.getBirthDate().toLocalDate());
        } else {
            entity.setBirthDate(null);
        }
        
        entity.setPhone(dto.getPhone());

        // Lógica de actualización de dirección
        if (dto.getAddress() != null) {
            Address a = entity.getAddress();
            if (a == null) {
                a = new Address();
                entity.setAddress(a);
                // a.setPatient(entity); // Si es bidireccional
            }
            // Actualizamos campos
            a.setStreet(dto.getAddress().getStreet());
            a.setIntNumber(dto.getAddress().getIntNumber());
            a.setExtNumber(dto.getAddress().getExtNumber());
            a.setNeighborhood(dto.getAddress().getNeighborhood());
            a.setCity(dto.getAddress().getCity());
            a.setZipCode(dto.getAddress().getZipCode());
        } 
        // Nota: Si dto.getAddress() es null, decides si borrar la dirección o no hacer nada.
        // Por seguridad, usualmente no se borra a menos que sea explícito.

        if (dto.getUserId() != null) {
            User u = new User();
            u.setUserId(dto.getUserId());
            entity.setUser(u);
        } else {
            entity.setUser(null);
        }
    }
}