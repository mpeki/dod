package dk.pekilidi.dod.util.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.data.BodyPartDTO;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.character.model.body.BodyPart;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.character.model.body.HumanoidBody;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.util.character.CharacterMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;

@Tag("regression")
class CharacterMapperTest {

  CharacterMapper mapper = new CharacterMapper();

  @Test
  void testNullBodyInSourceCharacterDTO() {
    CharacterDTO source = new CharacterDTO();
    assertThrows(MappingException.class, () -> mapper.map(source, DODCharacter.class));
  }

  @Test
  void testNullBodyInSourceCharacterEntity() {
    DODCharacter source = new DODCharacter("noname", "tester", new Race(), new HumanoidBody());
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

  @Test
  void testForBug_DOD_286() {
    assertThrows(java.lang.IllegalArgumentException.class, () -> mapper.map(null, DODCharacter.class));
    CharacterDTO source = CharacterDTO.builder().build();
    assertThrows(java.lang.IllegalArgumentException.class, () -> mapper.map(source, null));
    BodyPartDTO leftLeg = BodyPartDTO.builder().name(BodyPartName.LEFT_LEG).currentHP(4).maxHP(4).build();
    BodyPartDTO rightLeg = BodyPartDTO.builder().name(BodyPartName.RIGHT_LEG).currentHP(4).maxHP(4).build();
    source.addBodyPart(leftLeg);
    source.addBodyPart(rightLeg);
    assertThrows(org.modelmapper.MappingException.class, () -> mapper.map(source, DODCharacter.class));
    BodyPartDTO leftArm = BodyPartDTO.builder().name(BodyPartName.LEFT_ARM).currentHP(3).maxHP(3).build();
    BodyPartDTO rightArm = BodyPartDTO.builder().name(BodyPartName.RIGHT_ARM).currentHP(3).maxHP(3).build();
    BodyPartDTO head = BodyPartDTO.builder().name(BodyPartName.HEAD).currentHP(3).maxHP(3).build();
    BodyPartDTO chest = BodyPartDTO.builder().name(BodyPartName.CHEST).currentHP(3).maxHP(3).build();
    BodyPartDTO stomach = BodyPartDTO.builder().name(BodyPartName.STOMACH).currentHP(3).maxHP(3).build();
    BodyPartDTO total = BodyPartDTO.builder().name(BodyPartName.TOTAL).currentHP(3).maxHP(3).build();
    source.addBodyPart(leftArm);
    source.addBodyPart(rightArm);
    source.addBodyPart(head);
    source.addBodyPart(chest);
    source.addBodyPart(stomach);
    source.addBodyPart(total);

    DODCharacter result = mapper.map(source, DODCharacter.class);
    assertNotNull(result.getBody());
    assertTrue(result.getBody() instanceof HumanoidBody);
    assertEquals(((HumanoidBody) result.getBody()).getRightLeg().getMaxHP(), ((HumanoidBody) result.getBody()).getLeftLeg().getMaxHP());
  }


}
