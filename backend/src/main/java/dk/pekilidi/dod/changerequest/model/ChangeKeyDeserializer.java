package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.skill.SkillKey;
import java.io.IOException;

public class ChangeKeyDeserializer extends JsonDeserializer<ChangeKey> {

  @Override
  public ChangeKey deserialize(JsonParser jp, DeserializationContext context) throws IOException {
    String value = jp.getText();
    try {
      return BaseTraitName.valueOf(value);
    } catch (Exception e) {
      // Ignore
    }
    try {
      return BodyPartName.valueOf(value);
    } catch (Exception e) {
      // Ignore
    }
    try {
      return CharacterState.valueOf(value);
    } catch (Exception e) {
      // Ignore
    }
    return new SkillKey(value);
  }
}
