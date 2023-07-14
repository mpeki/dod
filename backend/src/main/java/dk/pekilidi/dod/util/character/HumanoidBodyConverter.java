package dk.pekilidi.dod.util.character;

import static dk.pekilidi.dod.character.model.body.BodyPartName.CHEST;
import static dk.pekilidi.dod.character.model.body.BodyPartName.HEAD;
import static dk.pekilidi.dod.character.model.body.BodyPartName.LEFT_ARM;
import static dk.pekilidi.dod.character.model.body.BodyPartName.LEFT_LEG;
import static dk.pekilidi.dod.character.model.body.BodyPartName.RIGHT_ARM;
import static dk.pekilidi.dod.character.model.body.BodyPartName.RIGHT_LEG;
import static dk.pekilidi.dod.character.model.body.BodyPartName.STOMACH;
import static dk.pekilidi.dod.character.model.body.BodyPartName.TOTAL;
import static dk.pekilidi.dod.character.model.body.HumanoidBody.TOTAL_BODY_PARTS;

import dk.pekilidi.dod.character.model.body.BodyPart;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.character.model.body.HumanoidBody;
import dk.pekilidi.dod.data.BodyPartDTO;
import java.util.EnumMap;
import java.util.Map;
import org.modelmapper.Converter;

public class HumanoidBodyConverter {

  private HumanoidBodyConverter() {
  }

  protected static Converter<Map<BodyPartName, BodyPartDTO>, HumanoidBody> toHumanoidEntity = context -> {
    Map<BodyPartName, BodyPartDTO> source = context.getSource();
    if(source.size() < TOTAL_BODY_PARTS){
      throw new BodyIncompleteException(context.getDestinationType(), source);
    }
    return HumanoidBody
        .builder()
        .total(createBodyPartEntityFromSourceDTO(TOTAL, source))
        .head(createBodyPartEntityFromSourceDTO(HEAD, source))
        .chest(createBodyPartEntityFromSourceDTO(CHEST, source))
        .stomach(createBodyPartEntityFromSourceDTO(STOMACH, source))
        .rightArm(createBodyPartEntityFromSourceDTO(RIGHT_ARM, source))
        .leftArm(createBodyPartEntityFromSourceDTO(LEFT_ARM, source))
        .rightLeg(createBodyPartEntityFromSourceDTO(RIGHT_LEG, source))
        .leftLeg(createBodyPartEntityFromSourceDTO(LEFT_LEG, source))
        .build();
  };

  protected static Converter<HumanoidBody, Map<BodyPartName, BodyPartDTO>> toHumanoidDTO = context -> initBodyPartsMap(
      context.getSource());

  private static EnumMap<BodyPartName, BodyPartDTO> initBodyPartsMap(HumanoidBody source) {

    return new EnumMap<>(Map.of(TOTAL, convertBodyPartToBodyPartDTO(TOTAL, source.getTotal()), HEAD,
        convertBodyPartToBodyPartDTO(HEAD, source.getHead()), CHEST,
        convertBodyPartToBodyPartDTO(CHEST, source.getChest()), STOMACH,
        convertBodyPartToBodyPartDTO(STOMACH, source.getStomach()), RIGHT_ARM,
        convertBodyPartToBodyPartDTO(RIGHT_ARM, source.getRightArm()), LEFT_ARM,
        convertBodyPartToBodyPartDTO(LEFT_ARM, source.getLeftArm()), RIGHT_LEG,
        convertBodyPartToBodyPartDTO(RIGHT_LEG, source.getRightLeg()), LEFT_LEG,
        convertBodyPartToBodyPartDTO(LEFT_LEG, source.getLeftLeg())));
  }

  private static BodyPartDTO convertBodyPartToBodyPartDTO(BodyPartName name, BodyPart sourceBodyPart) {
    return new BodyPartDTO(name, sourceBodyPart != null ? sourceBodyPart.getMaxHP() : -1,
        sourceBodyPart != null ? sourceBodyPart.getCurrentHP() : -1);
  }

  private static BodyPart createBodyPartEntityFromSourceDTO(BodyPartName bodyPartName,
      Map<BodyPartName, BodyPartDTO> source) {
    return BodyPart
        .builder()
        .maxHP(source.get(bodyPartName).getMaxHP())
        .currentHP(source.get(bodyPartName).getCurrentHP())
        .build();
  }
}
