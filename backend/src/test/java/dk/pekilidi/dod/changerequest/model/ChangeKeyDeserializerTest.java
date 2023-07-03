package dk.pekilidi.dod.changerequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.model.CharacterInfo;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ChangeKeyDeserializerTest {

  static final String newSkillChangeRequestJSON = "{\"changeType\": \"NEW_SKILL\", \"changeDescription\": \"Buy "
      + "Skill\", \"changeKey\": \"sense.danger\",\"modifier\": 1 }";

  static final String changeSkillRequestJSON = "{\"changeType\": \"SKILL_CHANGE\", \"changeDescription\": \"Buy Skill"
      + " Points\", \"changeKey\": \"sense.danger\",\"modifier\": 1 }";

  static final String newItemChangeRequestJSON = "{\"changeType\": \"NEW_ITEM\", \"changeDescription\": \"Buy Item\","
      + " \"changeKey\": \"hammer\",\"modifier\": 1 }";

  static final String newItemChangeRequestJSON_invalid_change = "{\"changeType\": \"HIT_POINTS\", "
      + "\"changeDescription\": \"Buy Item\", \"changeKey\": \"hammer\",\"modifier\": 1 }";

  static final String characterNameChangeRequestJSON = "{\"changeType\": \"CHARACTER_NAME_CHANGE\", "
      + "\"changeDescription\": \"Changing name\", \"changeKey\": \"NAME\",\"modifier\": \"Svend\" }";

  static final String invalidChangeRequestJSON = "{\"changeType\": \"NEW_ITEM\", \"changeDescription\": \"Florida\", "
      + "\"changeKey\": \"hammer\",\"modifier\": 1 }";

  static final String itemJSON = "{\"itemType\": \"MELEE_WEAPON\",\"breakable\": true,\"itemKey\": \"short.spear\","
      + "\"price\": 75,\"weight\": 2.0,\"bp\": 11,\"damage\": \"1t6+1\",\"handGrip\": \"ONE_OR_TWO_HANDED\","
      + "\"length\": 2,\"strengthGroup\": 1}";

  static final String skillJSON = """
      {
          "key": "shadow",
          "traitName": "INTELLIGENCE",
          "category": "A",
          "group": "THIEVING",
          "price": 2,
          "baseChance": "NONE"
        }""";

  static final String skillJSON_noKey = """
      {
          "key": "",
          "traitName": "INTELLIGENCE",
          "category": "A",
          "group": "THIEVING",
          "price": 2,
          "baseChance": "NONE"
        }""";

  @Test
  void test() {
    ChangeKeyDeserializer deserializer = new ChangeKeyDeserializer();
    assertThrows(NullPointerException.class, () -> deserializer.deserialize(null, null));
  }

  @Test
  void testDeserializeSkillChangeRequest() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper()
        .readerFor(ChangeRequest.class)
        .readValue(newSkillChangeRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(SkillKey.class, changeRequest.getChangeKey().getClass());
    assertEquals("sense.danger", ((SkillKey) changeRequest.getChangeKey()).getValue().getKeyValue());
  }

  @Test
  void testDeserializeSkillChangeRequest_modify_skill() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper().readerFor(ChangeRequest.class).readValue(changeSkillRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(SkillKey.class, changeRequest.getChangeKey().getClass());
    assertEquals("sense.danger", ((SkillKey) changeRequest.getChangeKey()).getValue().getKeyValue());
  }

  @Test
  void testDeserializeItemChangeRequest() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper().readerFor(ChangeRequest.class).readValue(newItemChangeRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(ItemKey.class, changeRequest.getChangeKey().getClass());
    assertEquals("hammer", ((ItemKey) changeRequest.getChangeKey()).getValue().getKeyValue());
  }

  @Test
  void testDeserializeNameChangeRequest() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper()
        .readerFor(ChangeRequest.class)
        .readValue(characterNameChangeRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(CharacterInfo.class, changeRequest.getChangeKey().getClass());
    assertEquals("NAME", ((CharacterInfo) changeRequest.getChangeKey()).name());
  }

  @Test
  void testDeserializeItemKey() throws JsonProcessingException {
    ItemDTO item = new ObjectMapper().readerFor(ItemDTO.class).readValue(itemJSON);
    assertNotNull(item.getItemKey());
    assertEquals(ItemKey.class, item.getItemKey().getClass());
    assertEquals("short.spear", item.getItemKey().getValue().getKeyValue());
  }

  @Test
  void testDeserializeSkillKey() throws JsonProcessingException {
    SkillDTO skill = new ObjectMapper().readerFor(SkillDTO.class).readValue(skillJSON);
    assertNotNull(skill.getKey());
    assertEquals(SkillKey.class, skill.getKey().getClass());
    assertEquals("shadow", skill.getKey().getValue().getKeyValue());
  }

  @Test
  void testDeserializeItemChangeRequest_no_key() throws JsonProcessingException {
    assertThrows(
        JsonMappingException.class,
        () -> new ObjectMapper().readerFor(ChangeRequest.class).readValue(newItemChangeRequestJSON_invalid_change));
  }

  /*
  @Test
  void testDeserializeItemChangeRequest_invalid_changeType() throws JsonProcessingException {
    assertThrows(JsonParseException.class, () -> new ObjectMapper()
        .readerFor(ChangeRequest.class)
        .readValue(invalidChangeRequestJSON));
  }
*/
}