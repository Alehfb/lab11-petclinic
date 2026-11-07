package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testFindById() {
        final Integer VET_ID = 2;
        final Integer SPECIALTY_ID = 1;

        VetSpecialtyDTO result = null;
        try {
            result = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(result);
        assertEquals(VET_ID, result.getVetId());
        assertEquals(SPECIALTY_ID, result.getSpecialtyId());
        assertEquals(8, result.getYearsExperience());
        assertTrue(result.getIsPrimary());
        assertEquals("Expert in X-ray imaging", result.getNotes());
    }

    @Test
    public void testFindSpecialtiesByVetId() {
        final Integer VET_ID = 3;
        final int EXPECTED_SIZE = 2;

        List<VetSpecialtyDTO> specialties = this.vetSpecialtyService.findByVetId(VET_ID);

        assertNotNull(specialties);
        assertEquals(EXPECTED_SIZE, specialties.size());
        specialties.forEach(vs -> assertEquals(VET_ID, vs.getVetId()));
    }

    @Test
    public void testFindVetsBySpecialtyId() {
        final Integer SPECIALTY_ID = 1;
        final int EXPECTED_SIZE = 2;

        List<VetSpecialtyDTO> vets = this.vetSpecialtyService.findBySpecialtyId(SPECIALTY_ID);

        assertNotNull(vets);
        assertEquals(EXPECTED_SIZE, vets.size());
        vets.forEach(vs -> assertEquals(SPECIALTY_ID, vs.getSpecialtyId()));
    }

    @Test
    public void testAssignSpecialtyToVet() {
        final Integer VET_ID = 1;
        final Integer SPECIALTY_ID = 2;
        final LocalDate CERT_DATE = LocalDate.of(2023, 1, 15);
        final Integer EXPERIENCE = 3;
        final Boolean IS_PRIMARY = true;
        final String NOTES = "Orthopedic surgeon certified in 2023";

        VetSpecialtyDTO dto = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(CERT_DATE)
                .yearsExperience(EXPERIENCE)
                .isPrimary(IS_PRIMARY)
                .notes(NOTES)
                .build();

        VetSpecialtyDTO created = this.vetSpecialtyService.create(dto);
        log.info("ASSIGNMENT CREATED: {}", created);

        assertNotNull(created);
        assertEquals(VET_ID, created.getVetId());
        assertEquals(SPECIALTY_ID, created.getSpecialtyId());
        assertEquals(EXPERIENCE, created.getYearsExperience());
        assertTrue(created.getIsPrimary());
        assertEquals(NOTES, created.getNotes());
    }

    @Test
    public void testUpdateSpecialtyAssignment() {
        VetSpecialtyDTO dto = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(3)
                .certificationDate(LocalDate.of(2022, 6, 1))
                .yearsExperience(2)
                .isPrimary(false)
                .notes("Junior dental assistant")
                .build();

        VetSpecialtyDTO created = this.vetSpecialtyService.create(dto);

        final String UP_NOTES = "Senior dental specialist";
        final Integer UP_EXP = 4;
        final Boolean UP_PRIMARY = true;

        created.setNotes(UP_NOTES);
        created.setYearsExperience(UP_EXP);
        created.setIsPrimary(UP_PRIMARY);

        VetSpecialtyDTO updated = this.vetSpecialtyService.update(created);
        log.info("ASSIGNMENT UPDATED: {}", updated);

        assertEquals(UP_NOTES, updated.getNotes());
        assertEquals(UP_EXP, updated.getYearsExperience());
        assertTrue(updated.getIsPrimary());
    }

    @Test
    public void testRemoveSpecialtyFromVet() {
        VetSpecialtyDTO dto = VetSpecialtyDTO.builder()
                .vetId(4)
                .specialtyId(1)
                .certificationDate(LocalDate.now())
                .yearsExperience(1)
                .isPrimary(false)
                .notes("Temporary association")
                .build();

        VetSpecialtyDTO created = this.vetSpecialtyService.create(dto);
        Integer vetId = created.getVetId();
        Integer specialtyId = created.getSpecialtyId();

        try {
            this.vetSpecialtyService.delete(vetId, specialtyId);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            this.vetSpecialtyService.findById(vetId, specialtyId);
        });
    }

    @Test
    public void testFindVetSpecialty_NotFound() {
        final Integer NON_EXISTING_VET = 9999;
        final Integer NON_EXISTING_SPEC = 9999;

        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            this.vetSpecialtyService.findById(NON_EXISTING_VET, NON_EXISTING_SPEC);
        });
    }
}
