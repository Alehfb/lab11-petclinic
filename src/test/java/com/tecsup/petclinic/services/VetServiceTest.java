package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {
        final Integer ID = 1;
        final String FIRST_NAME_EXPECTED = "James";
        final String LAST_NAME_EXPECTED = "Carter";

        VetDTO vet = null;
        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(vet);
        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
    }

    @Test
    public void testFindVetByLastName() {
        final String LAST_NAME = "Carter";
        final int EXPECTED_SIZE = 1;

        List<VetDTO> vets = this.vetService.findByLastName(LAST_NAME);

        assertNotNull(vets);
        assertEquals(EXPECTED_SIZE, vets.size());
        assertEquals("James", vets.get(0).getFirstName());
    }

    @Test
    public void testFindActiveVets() {
        List<Vet> activeVets = this.vetService.findByActive(true);

        assertNotNull(activeVets);
        assertTrue(activeVets.size() >= 5);
        activeVets.forEach(v -> assertTrue(v.getActive()));
    }

    @Test
    public void testCreateVet() {
        final String FIRST_NAME = "Carlos";
        final String LAST_NAME = "Santana";
        final String EMAIL = "carlos.santana@petclinic.com";
        final String PHONE = "6085559999";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(true)
                .build();

        VetDTO newVet = this.vetService.create(vetDTO);
        log.info("VET CREATED: {}", newVet);

        assertNotNull(newVet.getId());
        assertEquals(FIRST_NAME, newVet.getFirstName());
        assertEquals(LAST_NAME, newVet.getLastName());
        assertEquals(EMAIL, newVet.getEmail());
        assertEquals(PHONE, newVet.getPhone());
        assertTrue(newVet.getActive());
    }

    @Test
    public void testUpdateVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Ana")
                .lastName("Torres")
                .email("ana.torres@petclinic.com")
                .phone("6085551111")
                .active(true)
                .build();

        VetDTO createdVet = this.vetService.create(vetDTO);

        final String UP_FIRST_NAME = "Ana Maria";
        final String UP_PHONE = "6085552222";

        createdVet.setFirstName(UP_FIRST_NAME);
        createdVet.setPhone(UP_PHONE);

        VetDTO updatedVet = this.vetService.update(createdVet);
        log.info("VET UPDATED: {}", updatedVet);

        assertEquals(UP_FIRST_NAME, updatedVet.getFirstName());
        assertEquals(UP_PHONE, updatedVet.getPhone());
    }

    @Test
    public void testDeleteVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Temporal")
                .lastName("Borrar")
                .email("temporal.borrar@petclinic.com")
                .phone("6085550000")
                .active(true)
                .build();

        VetDTO createdVet = this.vetService.create(vetDTO);
        Integer id = createdVet.getId();

        try {
            this.vetService.delete(id);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> {
            this.vetService.findById(id);
        });
    }

    @Test
    public void testFindVetById_NotFound() {
        final Integer NON_EXISTING_ID = 9999;
        assertThrows(VetNotFoundException.class, () -> {
            this.vetService.findById(NON_EXISTING_ID);
        });
    }
}
