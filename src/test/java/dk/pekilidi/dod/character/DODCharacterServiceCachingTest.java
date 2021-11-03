package dk.pekilidi.dod.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class DODCharacterServiceCachingTest {
  @Autowired
  private DODCharacterService service;
  @MockBean
  private CharacterRepository charRepo;

  @Test
  public void testCharServiceCache() throws Exception{
    given(charRepo.findByName(anyString())).willReturn(new Being(0L,"kyron",new Race(0L,"tiefling")));
    service.getCharacterByName("kyron");
    service.getCharacterByName("kyron");
    verify(charRepo, times(1)).findByName("kyron");
  }

}
