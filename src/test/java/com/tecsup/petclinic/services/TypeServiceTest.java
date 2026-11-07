package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    @Test
    public void testFindTypeById() {
        final Integer ID = 1;
        final String NAME_EXPECTED = "cat";
        final String DESCRIPTION_EXPECTED = "Domestic feline";

        TypeDTO type = null;
        try {
            type = this.typeService.findById(ID);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(type);
        assertEquals(NAME_EXPECTED, type.getName());
        assertEquals(DESCRIPTION_EXPECTED, type.getDescription());
        assertTrue(type.getActive());
        assertEquals("small", type.getSizeCategory());
        assertEquals(15, type.getAverageLifespan());
        assertEquals("medium", type.getCareLevel());
    }

    @Test
    public void testFindByName() {
        final String NAME = "dog";
        final int EXPECTED_SIZE = 1;

        List<TypeDTO> types = this.typeService.findByName(NAME);

        assertNotNull(types);
        assertEquals(EXPECTED_SIZE, types.size());
        assertEquals("Domestic canine", types.get(0).getDescription());
    }

    @Test
    public void testFindActiveTypes() {
        List<Type> activeTypes = this.typeService.findByActive(true);

        assertNotNull(activeTypes);
        assertTrue(activeTypes.size() >= 7);
        activeTypes.forEach(t -> assertTrue(t.getActive()));
    }

    @Test
    public void testCreateType() {
        final String NAME = "ferret";
        final String DESCRIPTION = "Small domestic mustelid";
        final String SIZE_CATEGORY = "small";
        final Integer LIFESPAN = 8;
        final String CARE_LEVEL = "medium";

        TypeDTO typeDTO = TypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        TypeDTO created = this.typeService.create(typeDTO);
        log.info("TYPE CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(NAME, created.getName());
        assertEquals(DESCRIPTION, created.getDescription());
        assertTrue(created.getActive());
        assertEquals(SIZE_CATEGORY, created.getSizeCategory());
        assertEquals(LIFESPAN, created.getAverageLifespan());
        assertEquals(CARE_LEVEL, created.getCareLevel());
    }

    @Test
    public void testUpdateType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("parrot")
                .description("Tropical bird")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(40)
                .careLevel("high")
                .build();

        TypeDTO created = this.typeService.create(typeDTO);

        final String UP_DESC = "Talking tropical bird";
        final Integer UP_LIFESPAN = 50;

        created.setDescription(UP_DESC);
        created.setAverageLifespan(UP_LIFESPAN);

        TypeDTO updated = this.typeService.update(created);
        log.info("TYPE UPDATED: {}", updated);

        assertEquals(UP_DESC, updated.getDescription());
        assertEquals(UP_LIFESPAN, updated.getAverageLifespan());
    }

    @Test
    public void testDeleteType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("temporary_type")
                .description("Type to be deleted")
                .active(false)
                .sizeCategory("medium")
                .averageLifespan(1)
                .careLevel("low")
                .build();

        TypeDTO created = this.typeService.create(typeDTO);
        Integer id = created.getId();

        try {
            this.typeService.delete(id);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(TypeNotFoundException.class, () -> {
            this.typeService.findById(id);
        });
    }

    @Test
    public void testFindTypeById_NotFound() {
        final Integer NON_EXISTING_ID = 9999;
        assertThrows(TypeNotFoundException.class, () -> {
            this.typeService.findById(NON_EXISTING_ID);
        });
    }
}
