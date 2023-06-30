package dk.pekilidi.dod.changerequest.model;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ChangeKeyDeserializerTest {


  static final String newSkillChangeRequestJSON =
      "{\"changeType\": \"NEW_SKILL\", \"changeDescription\": \"Buy Skill\", \"changeKey\": \"sense.danger\",\"modifier\": 1 }";

  static final String changeSkillRequestJSON =
      "{\"changeType\": \"SKILL_CHANGE\", \"changeDescription\": \"Buy Skill Points\", \"changeKey\": \"sense.danger\",\"modifier\": 1 }";

  static final String newItemChangeRequestJSON =
      "{\"changeType\": \"NEW_ITEM\", \"changeDescription\": \"Buy Item\", \"changeKey\": \"hammer\",\"modifier\": 1 }";

  static final String invalidChangeRequestJSON =
      "{\"changeType\": \"NEW_ITEM\", \"changeDescription\": \"Florida\", \"changeKey\": \"hammer\",\"modifier\": 1 }";


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
    assertEquals("sense.danger", ((SkillKey)changeRequest.getChangeKey()).getValue().getKeyValue());
  }

  @Test
  void testDeserializeSkillChangeRequest_modify_skill() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper()
        .readerFor(ChangeRequest.class)
        .readValue(changeSkillRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(SkillKey.class, changeRequest.getChangeKey().getClass());
    assertEquals("sense.danger", ((SkillKey)changeRequest.getChangeKey()).getValue().getKeyValue());
  }

  @Test
  void testDeserializeItemChangeRequest() throws JsonProcessingException {
    ChangeRequest changeRequest = new ObjectMapper()
        .readerFor(ChangeRequest.class)
        .readValue(newItemChangeRequestJSON);
    assertNotNull(changeRequest.getChangeKey());
    assertEquals(ItemKey.class, changeRequest.getChangeKey().getClass());
    assertEquals("hammer", ((ItemKey)changeRequest.getChangeKey()).getValue().getKeyValue());
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