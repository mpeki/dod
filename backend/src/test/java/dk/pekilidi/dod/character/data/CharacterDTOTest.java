package dk.pekilidi.dod.character.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.BaseTraitDTO;
import dk.pekilidi.dod.data.CharacterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterDTOTest {

  CharacterDTO characterDTO;

  @BeforeEach
  void setUp() {
    characterDTO = new CharacterDTO();
  }

  @Test
  void requiredArgsConstructor() {
    characterDTO = CharacterDTO.builder().name("testName").build();
    assertEquals("testName", characterDTO.getName());
  }

  @Test
  void addBaseTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 0, 0));
    assertNotNull(characterDTO.getBaseTraits());
  }

  @Test
  void incrementTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
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
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertNotNull(characterDTO.getBaseTraits());
    assertEquals(1, characterDTO.getBaseTraits().size());
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(0, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
    characterDTO.decrementTrait(BaseTraitName.SIZE, 5);
    assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
  }

  @Test
  void updateBaseTrait() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertNotNull(characterDTO.getBaseTraits());
    characterDTO.updateBaseTrait(BaseTraitName.DEXTERITY, 7);
    assertEquals(7, characterDTO.getBaseTraits().get(BaseTraitName.DEXTERITY).getCurrentValue());
    characterDTO.updateBaseTrait(BaseTraitName.SIZE, 5);
    assertNull(characterDTO.getBaseTraits().get(BaseTraitName.SIZE));
  }

  @Test
  void groupValues() {
    assertEquals(0, characterDTO.getBaseTraits().size());
    assertEquals(-1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));
    characterDTO.addBaseTrait(new BaseTraitDTO(BaseTraitName.DEXTERITY, 5, 5));
    assertEquals(1, characterDTO.getBaseTraits().size());
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));
    characterDTO.decrementTrait(BaseTraitName.DEXTERITY, 2);
    assertEquals(3, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(0, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(4, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(8, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(1, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(9, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(2, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(12, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(2, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(13, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(3, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(16, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(3, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(17, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(4, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(20, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(4, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(21, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(5, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(25, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(5, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(26, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(6, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(30, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(6, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(31, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(32, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 3);
    assertEquals(35, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(40, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(7, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(41, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(8, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 9);
    assertEquals(50, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(8, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(51, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(9, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 10);
    assertEquals(61, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(10, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 50);
    assertEquals(111, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 5);
    assertEquals(116, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 4);
    assertEquals(120, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(15, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

    characterDTO.incrementTrait(BaseTraitName.DEXTERITY, 1);
    assertEquals(121, characterDTO.getBaseTraitValue(BaseTraitName.DEXTERITY));
    assertEquals(16, characterDTO.getBaseTraitGroupValue(BaseTraitName.DEXTERITY));

  }
}
