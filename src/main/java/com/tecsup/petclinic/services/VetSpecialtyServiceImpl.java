package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;

    @Override
    public VetSpecialtyDTO create(VetSpecialtyDTO dto) {
        VetSpecialty entity = vetSpecialtyRepository.save(vetSpecialtyMapper.mapToEntity(dto));
        return vetSpecialtyMapper.mapToDto(entity);
    }

    @Override
    public VetSpecialtyDTO update(VetSpecialtyDTO dto) {
        VetSpecialty entity = vetSpecialtyRepository.save(vetSpecialtyMapper.mapToEntity(dto));
        return vetSpecialtyMapper.mapToDto(entity);
    }

    @Override
    public void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialtyDTO dto = findById(vetId, specialtyId);
        vetSpecialtyRepository.delete(vetSpecialtyMapper.mapToEntity(dto));
    }

    @Override
    public VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        Optional<VetSpecialty> entity = vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId));
        if (!entity.isPresent()) {
            throw new VetSpecialtyNotFoundException("VetSpecialty record not found...!");
        }
        return vetSpecialtyMapper.mapToDto(entity.get());
    }

    @Override
    public List<VetSpecialtyDTO> findByVetId(Integer vetId) {
        List<VetSpecialty> list = vetSpecialtyRepository.findByVetId(vetId);
        list.forEach(item -> log.info("{}", item));
        return list.stream().map(vetSpecialtyMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId) {
        List<VetSpecialty> list = vetSpecialtyRepository.findBySpecialtyId(specialtyId);
        list.forEach(item -> log.info("{}", item));
        return list.stream().map(vetSpecialtyMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialty> findAll() {
        return vetSpecialtyRepository.findAll();
    }
}
