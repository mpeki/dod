package dk.pekilidi.dod.changerequest;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static dk.pekilidi.dod.character.model.CharacterState.INIT_COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeStatus;
import dk.pekilidi.dod.changerequest.model.ChangeStatusLabel;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class ChangeRequestServiceTest {

  @Autowired
  private CharacterService charService;
  @Autowired
  private ChangeRequestService changeRequestService;

  @Test
  void buyBasetraitIncrease() {

    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("tester").hero(true).race(new RaceDTO("human")).build());
    newChar.setState(INIT_COMPLETE);
    newChar.setHeroPoints(2);
    newChar = charService.save(newChar);
    int orgStrength = newChar.getBaseTraits().get(STRENGTH).getCurrentValue();
    ChangeRequest change = ChangeRequest
        .builder()
        .changeDescription("Increase strength")
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .modifier(1)
        .build();
    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    CharacterDTO result = (CharacterDTO) changeRequest.getObjectAfterChange();
    assertThat(result.getHeroPoints()).isZero();
    int newStrength = result.getBaseTraitValue(STRENGTH);
    assertThat(orgStrength).isNotEqualTo(newStrength);
    CharacterDTO loadedChar = charService.findCharacterById(newChar.getId());
    assertThat(orgStrength).isNotEqualTo(loadedChar.getBaseTraitValue(STRENGTH));
    assertThat(orgStrength + 1).isEqualTo(loadedChar.getBaseTraitValue(STRENGTH));
  }

  @Test
  void buyHeroPointIncrease() {

    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("tester").hero(true).race(new RaceDTO("human")).build());
    newChar.setState(INIT_COMPLETE);
    newChar = charService.save(newChar);
    int orgHeroPoints = newChar.getHeroPoints();
    ChangeRequest change = ChangeRequest
        .builder()
        .changeDescription("Increase hero points")
        .changeType(ChangeType.HERO_POINTS)
        .modifier(1)
        .build();
    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    CharacterDTO result = (CharacterDTO) changeRequest.getObjectAfterChange();
    assertThat(result.getHeroPoints()).isEqualTo(1);
    int newHeroPoints = result.getHeroPoints();
    assertThat(orgHeroPoints).isNotEqualTo(newHeroPoints);
    CharacterDTO loadedChar = charService.findCharacterById(newChar.getId());
    assertThat(orgHeroPoints + 1).isEqualTo(loadedChar.getHeroPoints());
  }

  @Test
  void buyBaseTraitIncreaseRejected() {

    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("tester").hero(true).race(new RaceDTO("human")).build());
    newChar.setState(INIT_COMPLETE);
    newChar = charService.save(newChar);
    int orgStrength = newChar.getBaseTraits().get(STRENGTH).getCurrentValue();
    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .modifier(1)
        .build();
    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    CharacterDTO result = (CharacterDTO) changeRequest.getObjectAfterChange();
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.INSUFFICIENT_HERO_POINTS);
    assertThat(orgStrength).isEqualTo(result.getBaseTraits().get(STRENGTH).getCurrentValue());
    assertThat(result.getHeroPoints()).isZero();
  }
}
