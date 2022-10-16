package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.*;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Test;

public class BeingTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  public void testConstructors() throws Exception {
    Being firstObject = new Being();
    assertThrows(NullPointerException.class, () -> {
      new Being(firstObject.getId(), firstObject.getName(), firstObject.getRace());
    });
    assertThrows(NullPointerException.class, () -> {
      new Being(0L, "test", null);
    });
  }

  @Test
  public void testExpectedNullPointers() throws Exception {
    Being firstObject = new Being();
    assertThrows(NullPointerException.class, () -> {
      firstObject.setName(null);
    });
    assertThrows(NullPointerException.class, () -> {
      firstObject.setRace(null);
    });
  }



  @Test
  public void testEquals() throws Exception {
    Being firstObject = rof.createAndFill(Being.class);
    Being secondObject = rof.createAndFill(Being.class);
    assertNotEquals(firstObject,secondObject);
    Being copied = new Being(firstObject.getId(), firstObject.getName(), firstObject.getRace());
    assertEquals(firstObject,copied);
    copied.setName("abe");
    assertNotEquals(firstObject,copied);
  }

  @Test
  public void testHashCode() throws Exception {
    Being firstObject = rof.createAndFill(Being.class);
    Being secondObject = rof.createAndFill(Being.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    Being copied = new Being(firstObject.getId(), firstObject.getName(), firstObject.getRace());
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setName("abe");
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}
