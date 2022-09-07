package dk.pekilidi.dod.character.data;

import dk.pekilidi.dod.character.BaseTraitName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDTOTest {

    CharacterDTO characterDTO;

    @BeforeEach
    void setUp() {
        characterDTO = new CharacterDTO();
    }

    @Test
    void requiredArgsConstructor(){
        characterDTO = CharacterDTO.builder().name("testName").build();
        assertEquals("testName", characterDTO.getName());
    }

    @Test
    void addBaseTrait() {
        assertEquals(0,characterDTO.getBaseTraits().size());
        characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
        characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
        characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
        characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0, 0));
        assertNotNull(characterDTO.getBaseTraits());
    }

    @Test
    void incrementTrait() {
        assertEquals(0, characterDTO.getBaseTraits().size());
        characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
        characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY,5,5,0));
        assertNotNull(characterDTO.getBaseTraits());
        characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
        assertEquals(10, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
        characterDTO.incrementTrait(BaseTraitName.SIZE, 5);
        assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
    }

    @Test
    void decrementTrait() {
        assertEquals(0, characterDTO.getBaseTraits().size());
        characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
        characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY,5,5,0));
        assertNotNull(characterDTO.getBaseTraits());
        characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
        assertEquals(0, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
        characterDTO.decrementTrait(BaseTraitName.SIZE, 5);
        assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
    }

    @Test
    void updateBaseTrait() {
        assertEquals(0, characterDTO.getBaseTraits().size());
        characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
        characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY,5,5,0));
        assertNotNull(characterDTO.getBaseTraits());
        characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
        assertEquals(7, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
        characterDTO.updateBaseTrait(BaseTraitName.SIZE, 5);
        assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
    }
}
