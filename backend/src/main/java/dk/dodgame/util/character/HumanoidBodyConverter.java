package dk.dodgame.util.character;

import java.util.EnumMap;
import java.util.Map;

import org.modelmapper.Converter;

import dk.dodgame.data.BodyPartDTO;
import dk.dodgame.domain.character.model.body.BodyPart;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.character.model.body.HumanoidBody;

public class HumanoidBodyConverter {

  private HumanoidBodyConverter() {
  }

  protected static Converter<Map<BodyPartName, BodyPartDTO>, HumanoidBody> toHumanoidEntity = context -> {
    Map<BodyPartName, BodyPartDTO> source = context.getSource();
    if(source.size() < HumanoidBody.TOTAL_BODY_PARTS){
      throw new BodyIncompleteException(context.getDestinationType(), source);
    }
    return HumanoidBody
        .builder()
        .total(createBodyPartEntityFromSourceDTO(BodyPartName.TOTAL, source))
        .head(createBodyPartEntityFromSourceDTO(BodyPartName.HEAD, source))
        .chest(createBodyPartEntityFromSourceDTO(BodyPartName.CHEST, source))
        .stomach(createBodyPartEntityFromSourceDTO(BodyPartName.STOMACH, source))
        .rightArm(createBodyPartEntityFromSourceDTO(BodyPartName.RIGHT_ARM, source))
        .leftArm(createBodyPartEntityFromSourceDTO(BodyPartName.LEFT_ARM, source))
        .rightLeg(createBodyPartEntityFromSourceDTO(BodyPartName.RIGHT_LEG, source))
        .leftLeg(createBodyPartEntityFromSourceDTO(BodyPartName.LEFT_LEG, source))
        .build();
  };

  protected static Converter<HumanoidBody, Map<BodyPartName, BodyPartDTO>> toHumanoidDTO = context -> initBodyPartsMap(
      context.getSource());

  private static EnumMap<BodyPartName, BodyPartDTO> initBodyPartsMap(HumanoidBody source) {

    return new EnumMap<>(Map.of(
        BodyPartName.TOTAL, convertBodyPartToBodyPartDTO(BodyPartName.TOTAL, source.getTotal()), BodyPartName.HEAD,
        convertBodyPartToBodyPartDTO(BodyPartName.HEAD, source.getHead()), BodyPartName.CHEST,
        convertBodyPartToBodyPartDTO(BodyPartName.CHEST, source.getChest()), BodyPartName.STOMACH,
        convertBodyPartToBodyPartDTO(BodyPartName.STOMACH, source.getStomach()), BodyPartName.RIGHT_ARM,
        convertBodyPartToBodyPartDTO(BodyPartName.RIGHT_ARM, source.getRightArm()), BodyPartName.LEFT_ARM,
        convertBodyPartToBodyPartDTO(BodyPartName.LEFT_ARM, source.getLeftArm()), BodyPartName.RIGHT_LEG,
        convertBodyPartToBodyPartDTO(BodyPartName.RIGHT_LEG, source.getRightLeg()), BodyPartName.LEFT_LEG,
        convertBodyPartToBodyPartDTO(BodyPartName.LEFT_LEG, source.getLeftLeg())));
  }

  private static BodyPartDTO convertBodyPartToBodyPartDTO(BodyPartName name, BodyPart sourceBodyPart) {
    return new BodyPartDTO(name, sourceBodyPart != null ? sourceBodyPart.getMaxHP() : -1,
        sourceBodyPart != null ? sourceBodyPart.getCurrentHP() : -1, 100);
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
