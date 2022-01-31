package dk.pekilidi.dod.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase
class DODCharacterServiceCachingTest {
  @Autowired
  private DODCharacterService service;
  @MockBean
  private CharacterRepository charRepo;

  @Test
  void testCharServiceCache() throws Exception{
    DODCharacter testBeing = new RandomObjectFiller().createAndFill(DODCharacter.class);
    List<DODCharacter> chars = new ArrayList<>(1);
    testBeing.setName("kyron");
    testBeing.setRace(new Race(null,"tiefling",null));
    chars.add(testBeing);
    given(charRepo.findByName(anyString())).willReturn(chars);
    service.getCharactersByName("kyron");
    service.getCharactersByName("kyron");
    verify(charRepo, times(1)).findByName("kyron");
  }

}
