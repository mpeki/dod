package dk.pekilidi.dod.util.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.character.model.body.BodyPart;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.character.model.body.HumanoidBody;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.util.character.CharacterMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterMapperTest {

  CharacterMapper mapper = new CharacterMapper();

  @Test
  void testNullBodyInSourceCharacterDTO() {
    CharacterDTO source = new CharacterDTO();
    DODCharacter result = mapper.map(source, DODCharacter.class);
    assertEquals("NONAME", result.getName());
    assertNotNull(result.getBody());
  }

  @Test
  void testNullBodyInSourceCharacterEntity() {
    DODCharacter source = new DODCharacter("noname", new Race(), new HumanoidBody());
    CharacterDTO result = mapper.map(source, CharacterDTO.class);
    assertNotNull(result.getBodyParts());
  }

  @Test
  void testTotalHPInSourceCharacterEntity() {
    DODCharacter source = new DODCharacter();
    source.setBody(HumanoidBody.builder().total(new BodyPart(10, 10)).build());
    CharacterDTO result = mapper.map(source, CharacterDTO.class);
    assertNotNull(result.getBodyParts());
    assertEquals(10, result.getBodyParts().get(BodyPartName.TOTAL).getCurrentHP());
    assertEquals(-1, result.getBodyParts().get(BodyPartName.CHEST).getCurrentHP());
  }
}
