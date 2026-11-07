package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testFindSpecialtyById() {
        final Integer ID = 1;
        final String NAME_EXPECTED = "radiology";
        final String OFFICE_EXPECTED = "Farewell";

        SpecialtyDTO specialty = null;
        try {
            specialty = this.specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(specialty);
        assertEquals(NAME_EXPECTED, specialty.getName());
        assertEquals(OFFICE_EXPECTED, specialty.getOffice());
        assertEquals(8, specialty.getOpenHour());
        assertEquals(18, specialty.getCloseHour());
    }

    @Test
    public void testFindByName() {
        final String NAME = "surgery";
        final int EXPECTED_SIZE = 1;

        List<SpecialtyDTO> specialties = this.specialtyService.findByName(NAME);

        assertNotNull(specialties);
        assertEquals(EXPECTED_SIZE, specialties.size());
        assertEquals("Maryland", specialties.get(0).getOffice());
    }

    @Test
    public void testFindByOffice() {
        final String OFFICE = "Terranova";
        final int EXPECTED_SIZE = 1;

        List<SpecialtyDTO> specialties = this.specialtyService.findByOffice(OFFICE);

        assertNotNull(specialties);
        assertEquals(EXPECTED_SIZE, specialties.size());
        assertEquals("dentistry", specialties.get(0).getName());
    }

    @Test
    public void testCreateSpecialty() {
        final String NAME = "dermatology";
        final String OFFICE = "Greenville";
        final Integer OPEN_HOUR = 8;
        final Integer CLOSE_HOUR = 17;

        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name(NAME)
                .office(OFFICE)
                .openHour(OPEN_HOUR)
                .closeHour(CLOSE_HOUR)
                .build();

        SpecialtyDTO created = this.specialtyService.create(specialtyDTO);
        log.info("SPECIALTY CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(NAME, created.getName());
        assertEquals(OFFICE, created.getOffice());
        assertEquals(OPEN_HOUR, created.getOpenHour());
        assertEquals(CLOSE_HOUR, created.getCloseHour());
    }

    @Test
    public void testUpdateSpecialty() {
        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name("oncology")
                .office("East Wing")
                .openHour(9)
                .closeHour(15)
                .build();

        SpecialtyDTO created = this.specialtyService.create(specialtyDTO);

        final String UP_OFFICE = "West Wing";
        final Integer UP_CLOSE = 18;

        created.setOffice(UP_OFFICE);
        created.setCloseHour(UP_CLOSE);

        SpecialtyDTO updated = this.specialtyService.update(created);
        log.info("SPECIALTY UPDATED: {}", updated);

        assertEquals(UP_OFFICE, updated.getOffice());
        assertEquals(UP_CLOSE, updated.getCloseHour());
    }

    @Test
    public void testDeleteSpecialty() {
        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name("temporary")
                .office("Temp Office")
                .openHour(10)
                .closeHour(14)
                .build();

        SpecialtyDTO created = this.specialtyService.create(specialtyDTO);
        Integer id = created.getId();

        try {
            this.specialtyService.delete(id);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class, () -> {
            this.specialtyService.findById(id);
        });
    }

    @Test
    public void testFindSpecialtyById_NotFound() {
        final Integer NON_EXISTING_ID = 9999;
        assertThrows(SpecialtyNotFoundException.class, () -> {
            this.specialtyService.findById(NON_EXISTING_ID);
        });
    }
}
