package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import java.io.IOException;

public class ChangeKeyDeserializer extends JsonDeserializer<ChangeKey> {

  @Override
  public ChangeKey deserialize(JsonParser jp, DeserializationContext context) throws IOException {
    String text = jp.getText();
    Object value = jp.getCurrentValue();

    try {
      return BaseTraitName.valueOf(text);
    } catch (Exception e) {
      // Ignore
    }
    try {
      return BodyPartName.valueOf(text);
    } catch (Exception e) {
      // Ignore
    }
    try {
      return CharacterState.valueOf(text);
    } catch (Exception e) {
      // Ignore
    }
    if(value instanceof ItemDTO){
      return new ItemKey(text);
    }
    if(value instanceof SkillDTO){
      return new SkillKey(text);
    }
    if(value instanceof ChangeRequest) {
      ChangeRequest changeRequest = (ChangeRequest) value;
      if(changeRequest.getChangeType().equals(ChangeType.NEW_ITEM)){
        return new ItemKey(text);
      } else if(changeRequest.getChangeType().equals(ChangeType.NEW_SKILL) || changeRequest.getChangeType().equals(ChangeType.SKILL_CHANGE)) {
        return new SkillKey(text);
      }
    }
    throw new JsonParseException(jp, "Tried everything, but can't parse ChangeKey property: " + jp.currentName());
  }
}

