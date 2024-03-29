package dk.dodgame.domain.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.dodgame.util.RandomObjectFiller;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class BaseTraitTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testConstructors() throws Exception {
    BaseTrait firstObject = new BaseTrait();
    String id = firstObject.getId();
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
    BaseTrait copied = new BaseTrait(firstObject.getId(), firstObject.getTraitName(), firstObject.getCurrentValue(),
        firstObject.getStartValue(), firstObject.getGroupValue());
    assertEquals(firstObject, copied);
    copied.setCurrentValue(12);
    assertNotEquals(firstObject, copied);
    copied.setCurrentValue(firstObject.getCurrentValue());
    assertEquals(firstObject, copied);
    if (copied.getTraitName().equals(BaseTraitName.CONSTITUTION)) {
      copied.setTraitName(BaseTraitName.STRENGTH);
    } else {
      copied.setTraitName(BaseTraitName.CONSTITUTION);
    }
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsSame() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testEqualsNull() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(null, firstObject);
  }

  @Test
  void testEqualsAnotherClass() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(firstObject, new Object());
  }

  @Test
  void testHashCode() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    firstObject.setTraitName(BaseTraitName.SIZE);
    BaseTrait secondObject = rof.createAndFill(BaseTrait.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    BaseTrait copied = new BaseTrait(firstObject.getId(), firstObject.getTraitName(), firstObject.getCurrentValue(),
        firstObject.getStartValue(), firstObject.getGroupValue());
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setTraitName(BaseTraitName.STRENGTH);
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }

  @Test
  void testToString() throws Exception {
    BaseTrait firstObject = rof.createAndFill(BaseTrait.class);
    assertTrue(firstObject.toString().contains("id="));
    assertTrue(firstObject.toString().contains("traitName="));
  }
}
