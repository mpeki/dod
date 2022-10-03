package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.skill.SkillKey;
import java.io.IOException;

public class ChangeKeyDeserializer extends JsonDeserializer {

  @Override
  public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
    String value = jp.getText();
    try {
      BaseTraitName.valueOf(value);
      return BaseTraitName.valueOf(value);
    } catch (Exception e) {
    }
    return new SkillKey(value);
  }
}
