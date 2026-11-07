package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

    VetSpecialtyDTO create(VetSpecialtyDTO dto);

    VetSpecialtyDTO update(VetSpecialtyDTO dto);

    void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<VetSpecialtyDTO> findByVetId(Integer vetId);

    List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId);

    List<VetSpecialty> findAll();
}
