package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testcontainers.utility.Base58.randomString;

import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class BaseTraitRuleTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    BaseTraitRule secondObject = rof.createAndFill(BaseTraitRule.class);
    assertNotEquals(firstObject, secondObject);
    BaseTraitRule copied = firstObject.toBuilder().build();
    assertEquals(firstObject, copied);
    copied.setId(randomString(10));
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsSame() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testEqualsNull() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    assertNotEquals(null, firstObject);
  }
  @Test
  void testEqualsOtherObject() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    assertNotEquals(firstObject, new Object());
  }

  @Test
  void testHashCode() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    BaseTraitRule secondObject = rof.createAndFill(BaseTraitRule.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    BaseTraitRule copied = firstObject.toBuilder().build();
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setId(randomString(10));
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}
