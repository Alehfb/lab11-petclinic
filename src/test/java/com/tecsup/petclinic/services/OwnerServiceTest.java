package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById() {
        final Integer ID = 1;
        final String FIRST_NAME_EXPECTED = "George";
        final String LAST_NAME_EXPECTED = "Franklin";

        OwnerDTO owner = null;
        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(owner);
        assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, owner.getLastName());
    }

    @Test
    public void testFindByLastName() {
        final String LAST_NAME = "Davis";
        final int EXPECTED_SIZE = 2;

        List<OwnerDTO> owners = this.ownerService.findByLastName(LAST_NAME);

        assertNotNull(owners);
        assertEquals(EXPECTED_SIZE, owners.size());
    }

    @Test
    public void testFindByCity() {
        final String CITY = "Madison";
        final int MIN_EXPECTED_SIZE = 4;

        List<OwnerDTO> owners = this.ownerService.findByCity(CITY);

        assertNotNull(owners);
        assertTrue(owners.size() >= MIN_EXPECTED_SIZE);
        owners.forEach(o -> assertEquals(CITY, o.getCity()));
    }

    @Test
    public void testCreateOwner() {
        final String FIRST_NAME = "Alexander";
        final String LAST_NAME = "Follano";
        final String ADDRESS = "Av. La Marina 200";
        final String CITY = "Lima";
        final String TELEPHONE = "987654321";

        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        OwnerDTO createdOwner = this.ownerService.create(ownerDTO);
        log.info("OWNER CREATED: {}", createdOwner);

        assertNotNull(createdOwner.getId());
        assertEquals(FIRST_NAME, createdOwner.getFirstName());
        assertEquals(LAST_NAME, createdOwner.getLastName());
        assertEquals(ADDRESS, createdOwner.getAddress());
        assertEquals(CITY, createdOwner.getCity());
        assertEquals(TELEPHONE, createdOwner.getTelephone());
    }

    @Test
    public void testUpdateOwner() {
        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName("Luis")
                .lastName("Mendoza")
                .address("Calle 10 321")
                .city("Arequipa")
                .telephone("955112233")
                .build();

        OwnerDTO createdOwner = this.ownerService.create(ownerDTO);

        final String UP_ADDRESS = "Calle 10 456";
        final String UP_TELEPHONE = "955998877";

        createdOwner.setAddress(UP_ADDRESS);
        createdOwner.setTelephone(UP_TELEPHONE);

        OwnerDTO updatedOwner = this.ownerService.update(createdOwner);
        log.info("OWNER UPDATED: {}", updatedOwner);

        assertEquals(UP_ADDRESS, updatedOwner.getAddress());
        assertEquals(UP_TELEPHONE, updatedOwner.getTelephone());
    }

    @Test
    public void testDeleteOwner() {
        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName("Borrar")
                .lastName("Temporal")
                .address("Sin direccion")
                .city("Sin ciudad")
                .telephone("000000000")
                .build();

        OwnerDTO createdOwner = this.ownerService.create(ownerDTO);
        Integer id = createdOwner.getId();

        try {
            this.ownerService.delete(id);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(OwnerNotFoundException.class, () -> {
            this.ownerService.findById(id);
        });
    }

    @Test
    public void testFindOwnerById_NotFound() {
        final Integer NON_EXISTING_ID = 9999;
        assertThrows(OwnerNotFoundException.class, () -> {
            this.ownerService.findById(NON_EXISTING_ID);
        });
    }
}
