package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Test;

class BaseTraitRuleTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    BaseTraitRule firstObject = rof.createAndFill(BaseTraitRule.class);
    BaseTraitRule secondObject = rof.createAndFill(BaseTraitRule.class);
    assertNotEquals(firstObject, secondObject);
    BaseTraitRule copied = firstObject.toBuilder().build();
    assertEquals(firstObject, copied);
    copied.setId(0L);
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
}
