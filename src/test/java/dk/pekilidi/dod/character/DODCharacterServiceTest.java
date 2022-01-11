package dk.pekilidi.dod.character;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DODCharacterServiceTest {

  private CharacterRepository charRepo;
  private DODCharacterService charService;

  @BeforeEach
  void setMockOutput() {
    charRepo = mock(CharacterRepository.class);
    charService = new DODCharacterService(charRepo);
    when(charRepo.findByName("abe")).thenReturn(null);
  }

  @Test
  public void getCharacterReturnChar(){
    Being testChar = new Being(0L,"kyron",new Race(0L,"tiefling"));
    given(charRepo.findByName("kyron")).willReturn(testChar);
    Being being = charService.getCharacterByName("kyron");
    assertThat(being.getName()).isEqualTo("kyron");
    assertThat(being.getRace().getName()).isEqualTo("tiefling");
  }

  @Test
  public void testPojoMethods(){
    Race testRace = new Race(12L,"tiefling");
    Being testChar = new Being(13L,"kyron",testRace);
    given(charRepo.findByName("kyron")).willReturn(testChar);
    Being being = charService.getCharacterByName("kyron");
    assertThat(being).isEqualTo(testChar);
    assertThat(being.toString()).isEqualTo(testChar.toString());
  }


  @Test
  public void getCharacterReturnCharNotFound() throws Exception {
    assertThrows(CharacterNotFoundException.class, () -> {
      charService.getCharacterByName("abe");
    });
  }

}
