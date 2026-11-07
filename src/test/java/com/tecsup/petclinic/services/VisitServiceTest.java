package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    public void testFindVisitById() {
        final Integer ID = 1;
        final Integer PET_ID_EXPECTED = 7;
        final Integer VET_ID_EXPECTED = 2;
        final String DESCRIPTION_EXPECTED = "rabies shot";

        VisitDTO visit = null;
        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(visit);
        assertEquals(PET_ID_EXPECTED, visit.getPetId());
        assertEquals(VET_ID_EXPECTED, visit.getVetId());
        assertEquals(DESCRIPTION_EXPECTED, visit.getDescription());
        assertEquals(0, new BigDecimal("45.00").compareTo(visit.getCost()));
    }

    @Test
    public void testFindByPetId() {
        final Integer PET_ID = 7;
        final int EXPECTED_SIZE = 2;

        List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

        assertNotNull(visits);
        assertEquals(EXPECTED_SIZE, visits.size());
        visits.forEach(v -> assertEquals(PET_ID, v.getPetId()));
    }

    @Test
    public void testFindByVetId() {
        final Integer VET_ID = 2;
        final int EXPECTED_SIZE = 2;

        List<VisitDTO> visits = this.visitService.findByVetId(VET_ID);

        assertNotNull(visits);
        assertEquals(EXPECTED_SIZE, visits.size());
        visits.forEach(v -> assertEquals(VET_ID, v.getVetId()));
    }

    @Test
    public void testCreateVisit() {
        final Integer PET_ID = 1;
        final Integer VET_ID = 1;
        final LocalDate VISIT_DATE = LocalDate.now();
        final String DESCRIPTION = "Annual general checkup";
        final BigDecimal COST = new BigDecimal("85.50");

        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .cost(COST)
                .build();

        VisitDTO created = this.visitService.create(visitDTO);
        log.info("VISIT CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(PET_ID, created.getPetId());
        assertEquals(VET_ID, created.getVetId());
        assertEquals(VISIT_DATE, created.getVisitDate());
        assertEquals(DESCRIPTION, created.getDescription());
        assertEquals(0, COST.compareTo(created.getCost()));
    }

    @Test
    public void testUpdateVisit() {
        VisitDTO visitDTO = VisitDTO.builder()
                .petId(2)
                .vetId(3)
                .visitDate(LocalDate.of(2025, 5, 10))
                .description("Initial consultation")
                .cost(new BigDecimal("50.00"))
                .build();

        VisitDTO created = this.visitService.create(visitDTO);

        final String UP_DESCRIPTION = "Initial consultation and blood test";
        final BigDecimal UP_COST = new BigDecimal("120.00");

        created.setDescription(UP_DESCRIPTION);
        created.setCost(UP_COST);

        VisitDTO updated = this.visitService.update(created);
        log.info("VISIT UPDATED: {}", updated);

        assertEquals(UP_DESCRIPTION, updated.getDescription());
        assertEquals(0, UP_COST.compareTo(updated.getCost()));
    }

    @Test
    public void testDeleteVisit() {
        VisitDTO visitDTO = VisitDTO.builder()
                .petId(3)
                .vetId(4)
                .visitDate(LocalDate.now())
                .description("Temporary visit for deletion")
                .cost(new BigDecimal("30.00"))
                .build();

        VisitDTO created = this.visitService.create(visitDTO);
        Integer id = created.getId();

        try {
            this.visitService.delete(id);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> {
            this.visitService.findById(id);
        });
    }

    @Test
    public void testFindVisitById_NotFound() {
        final Integer NON_EXISTING_ID = 9999;
        assertThrows(VisitNotFoundException.class, () -> {
            this.visitService.findById(NON_EXISTING_ID);
        });
    }
}
