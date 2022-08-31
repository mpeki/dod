package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Test;

class BaseTraitTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testConstructors() throws Exception {
    BaseTrait firstObject = new BaseTrait();
    Long id = firstObject.getId();
    BaseTraitName traitName = firstObject.getTraitName();

    assertThrows(NullPointerException.class, () -> {
      new BaseTrait(id, null, 0, 0, 0);
    });
    assertThrows(NullPointerException.class, () -> {
      new BaseTrait(null, traitName, 0, 0, 0);
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
    assertNotEquals(firstObject, secondObject);
    BaseTrait copied = new BaseTrait(
        firstObject.getId(), firstObject.getTraitName(), firstObject.getValue(), firstObject.getStartValue(),
        firstObject.getGroupValue());
    assertEquals(firstObject, copied);
    copied.setValue(12);
    assertNotEquals(firstObject, copied);
    copied.setValue(firstObject.getValue());
    assertEquals(firstObject, copied);
    copied.setTraitName(BaseTraitName.CONSTITUTION);
    assertNotEquals(firstObject, copied);

  }

  @Test
  void testEqualsSame() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertEquals(firstObject,firstObject);
  }

  @Test
  void testEqualsNull() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(null, firstObject);
  }

  @Test
  void testEqualsAnotherClass() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals("HAT", firstObject);
  }


  @Test
  void testHashCode() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    firstObject.setTraitName(BaseTraitName.SIZE);
    BaseTrait secondObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    BaseTrait copied = new BaseTrait(
        firstObject.getId(), firstObject.getTraitName(), firstObject.getValue(), firstObject.getStartValue(),
        firstObject.getGroupValue());
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setTraitName(BaseTraitName.STRENGTH);
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }

  @Test
  void testToString() throws Exception{
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertTrue(firstObject.toString().contains("id="));
    assertTrue(firstObject.toString().contains("traitName="));
  }

}
