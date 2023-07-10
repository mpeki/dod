package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterInfo;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import java.io.IOException;
import java.util.Objects;

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
    try {
      return CharacterInfo.valueOf(text);
    } catch (Exception e) {
      // Ignore
    }
    if(value instanceof ItemDTO){
      return new ItemKey(text);
    }
    if(value instanceof SkillDTO){
      return new SkillKey(text);
    }
    if(value instanceof ChangeRequest changeRequest) {
      switch (changeRequest.getChangeType()) {
        case NEW_ITEM -> {
          return new ItemKey(text);
        }
        case NEW_SKILL, SKILL_CHANGE -> {
          return new SkillKey(text);
        }
        case CHARACTER_NAME_CHANGE -> {
          return CharacterInfo.valueOf(text);
        }
        default -> throw new IllegalStateException("Unexpected value: " + changeRequest.getChangeType());
      }
    }
    if(value instanceof SecondaryChangeKey secondaryChangeKey) {
      if (Objects.requireNonNull(secondaryChangeKey.getChangeType()) == ChangeType.SKILL_FOR_ITEM_USE) {
        return new ItemKey(text);
      }
      throw new IllegalStateException("Unexpected Secondary ChangeType: " + secondaryChangeKey.getChangeType());
    }
    throw new JsonParseException(jp, "Tried everything, but can't parse ChangeKey property: " + jp.currentName());
  }
}

