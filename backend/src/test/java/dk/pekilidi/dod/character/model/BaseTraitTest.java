package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseTraitTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testConstructors() throws Exception {
    BaseTrait firstObject = new BaseTrait();
    Long id = firstObject.getId();
    BaseTraitName traitName = firstObject.getTraitName();
    DODCharacter character = firstObject.getCharacter();

    assertThrows(NullPointerException.class, () -> {
      new BaseTrait(id,character,null, 0,0,0);
    });
    assertThrows(NullPointerException.class, () -> {
      new BaseTrait(null,character,traitName, 0,0,0);
    });
  }

  @Test
  void testExpectedNullPointers() throws Exception {
    BaseTrait firstObject = new BaseTrait();
    assertThrows(NullPointerException.class, () -> {
      firstObject.setTraitName(null);
    });
  }



  @Test
  void testEquals() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    BaseTrait secondObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(firstObject,secondObject);
    BaseTrait copied = new BaseTrait(firstObject.getId(), firstObject.getCharacter(),firstObject.getTraitName(), firstObject.getValue(), firstObject.getStartValue(), firstObject.getGroupValue());
    assertEquals(firstObject,copied);
    copied.setValue(12);
    assertNotEquals(firstObject,copied);
  }

  @Test
  void testHashCode() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    firstObject.setTraitName(BaseTraitName.SIZE);
    BaseTrait secondObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    BaseTrait copied = new BaseTrait(firstObject.getId(), firstObject.getCharacter(),firstObject.getTraitName(), firstObject.getValue(), firstObject.getStartValue(), firstObject.getGroupValue());
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setTraitName(BaseTraitName.STRENGTH);
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}
