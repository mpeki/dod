package dk.pekilidi.dod.changerequest;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static dk.pekilidi.dod.character.model.CharacterState.INIT_COMPLETE;
import static dk.pekilidi.dod.character.model.CharacterState.READY_TO_PLAY;
import static org.assertj.core.api.Assertions.assertThat;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeStatus;
import dk.pekilidi.dod.changerequest.model.ChangeStatusLabel;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.character.model.CharacterInfo;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import dk.pekilidi.dod.skill.SkillService;
import dk.pekilidi.dod.skill.model.Category;
import dk.pekilidi.dod.skill.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class CharacterChangeTest {

  @Autowired
  private CharacterService charService;
  @Autowired
  private ChangeRequestService changeRequestService;
  @Autowired
  private SkillService skillService;

  CharacterDTO testChar;
  @BeforeEach
  void setup() {
    testChar = charService.createCharacter(
        CharacterDTO.builder().name("unwanted-name").race(new RaceDTO("human")).build());
  }

  @Test
  void testCharacterNameChange_invalid_name(){
    testChar.setState(INIT_COMPLETE);
    testChar = charService.save(testChar);
    ChangeRequest change = ChangeRequest
        .builder()
        .changeDescription("Change Name!")
        .changeType(ChangeType.CHARACTER_CHANGE_NAME)
        .changeKey(CharacterInfo.NAME)
        .modifier("2")
        .build();

    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(testChar.getId(), change);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.INVALID_NAME);
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);

  }

  @Test
  void testCharacterNameChange_invalid_state(){

    testChar.setState(READY_TO_PLAY);
    charService.save(testChar);

    ChangeRequest change = ChangeRequest
        .builder()
        .changeDescription("Change Name!")
        .changeType(ChangeType.CHARACTER_CHANGE_NAME)
        .changeKey(CharacterInfo.NAME)
        .modifier("Boris")
        .build();

    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(testChar.getId(), change);
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.WRONG_CHARACTER_STATE);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/valid-char-names.csv", numLinesToSkip = 1)
  void testCharacterNameChange_name_ok(String validName){

    testChar.setState(INIT_COMPLETE);
    charService.save(testChar);

    ChangeRequest change = ChangeRequest
        .builder()
        .changeDescription("Change Name!")
        .changeType(ChangeType.CHARACTER_CHANGE_NAME)
        .changeKey(CharacterInfo.NAME)
        .modifier(validName)
        .build();

    change = changeRequestService.submitChangeRequest(testChar.getId(), change);
    assertThat(change.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_NAME);
    assertThat(change.getStatus()).isEqualTo(ChangeStatus.APPROVED);
  }
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
    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .modifier(1)
        .build();
    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    assertThat(changeRequest.getObjectAfterChange()).isNull();
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.INSUFFICIENT_HERO_POINTS);
  }

  @ParameterizedTest
  @CsvSource({
      "'char1', 'primary.weapon'",
      "'char2', 'secondary.weapon'",
      "'char3', 'skill.that.does.not.exist'",
      "'char4', 'primary.weapon'"})
  void buyNewSkill(String charName, String skillName) {
    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name(charName).hero(true).race(new RaceDTO("human")).build());

    SkillDTO skill = SkillDTO.builder().group(Group.COMBAT).key(SkillKey.builder().value(skillName).build()).build();

    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.NEW_SKILL)
        .changeKey(skill.getKey())
        .modifier(13)
        .build();

    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);

    if (skillName.equals("skill.that.does.not.exist")) {
      assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
      assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.SKILL_DOES_NOT_EXIST);
      assertThat(changeRequest.getObjectAfterChange()).isNull();
    } else {
      assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.APPROVED);
      assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_SKILL_BOUGHT);
      assertThat(changeRequest.getObjectAfterChange()).isNotNull();
    }
  }

  @Test
  void buyDuplicateSkillShouldFail() {
    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("test-char-duplicate").hero(true).race(new RaceDTO("human")).build());

    SkillDTO skill = SkillDTO
        .builder()
        .group(Group.COMBAT)
        .key(SkillKey.builder().value("primary.weapon").build())
        .build();

    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.NEW_SKILL)
        .changeKey(skill.getKey())
        .modifier(11)
        .build();

    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);

    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.APPROVED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_SKILL_BOUGHT);
    assertThat(changeRequest.getObjectAfterChange()).isNotNull();

    changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.SKILL_ALREADY_BOUGHT);
  }

  @Test
  void testBuyingMultipleSkills() {
    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("test-char-all").hero(true).race(new RaceDTO("human")).build());
    for (SkillDTO skill : skillService.getSkills()) {
      ChangeRequest change = ChangeRequest
            .builder()
            .changeType(ChangeType.NEW_SKILL)
            .changeKey(skill.getKey())
            .modifier(skill.getCategory() == Category.A ? 6 : 1)
            .build();

      ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
      assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.APPROVED);
      assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_SKILL_BOUGHT);
      assertThat(changeRequest.getObjectAfterChange()).isNotNull();
      CharacterDTO character = charService.findCharacterById(newChar.getId());
      System.out.println("Num char skills: " + character.getSkills().size() + " skill points left: " + character.getBaseSkillPoints());
      if(character.getBaseSkillPoints() < 15) {
        break;
      }
    }
  }

  @Test
  void testMultipleCharsBuyingMultipleSkills() {
    CharacterDTO newChar1 = charService.createCharacter(
        CharacterDTO.builder().name("test-char-1").hero(true).race(new RaceDTO("human")).build());
    CharacterDTO newChar2 = charService.createCharacter(
        CharacterDTO.builder().name("test-char-2").hero(true).race(new RaceDTO("human")).build());

    for (SkillDTO skill : skillService.getSkills()) {
      ChangeRequest change1 = ChangeRequest
          .builder()
          .changeType(ChangeType.NEW_SKILL)
          .changeKey(skill.getKey())
          .modifier(skill.getCategory() == Category.A ? 6 : 1)
          .build();
      ChangeRequest change2 = ChangeRequest
          .builder()
          .changeType(ChangeType.NEW_SKILL)
          .changeKey(skill.getKey())
          .modifier(skill.getCategory() == Category.A ? 6 : 1)
          .build();

      ChangeRequest changeRequest1 = changeRequestService.submitChangeRequest(newChar1.getId(), change1);
      if(changeRequest1.getStatus() == ChangeStatus.APPROVED) {
        assertThat(changeRequest1.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_SKILL_BOUGHT);
        assertThat(changeRequest1.getObjectAfterChange()).isNotNull();
        CharacterDTO character1 = charService.findCharacterById(newChar1.getId());
        assertThat(character1.getSkills()).containsKey(skill.getKey().getKeyValue());
      } else {
        assertThat(changeRequest1.getStatusLabel()).isEqualTo(ChangeStatusLabel.INSUFFICIENT_SKILL_POINTS);
        break;
      }

      ChangeRequest changeRequest2 = changeRequestService.submitChangeRequest(newChar2.getId(), change2);
      if(changeRequest2.getStatus() == ChangeStatus.APPROVED) {
        assertThat(changeRequest2.getStatusLabel()).isEqualTo(ChangeStatusLabel.OK_SKILL_BOUGHT);
        assertThat(changeRequest2.getObjectAfterChange()).isNotNull();
        CharacterDTO character2 = charService.findCharacterById(newChar2.getId());
        assertThat(character2.getSkills()).containsKey(skill.getKey().getKeyValue());
      } else {
        assertThat(changeRequest2.getStatusLabel()).isEqualTo(ChangeStatusLabel.INSUFFICIENT_SKILL_POINTS);
        break;
      }
    }
  }

  @Test
  void testChangeWithNoMatchingRules(){
    CharacterDTO newChar = charService.createCharacter(
        CharacterDTO.builder().name("test-char-no-rules").hero(true).race(new RaceDTO("human")).build());
    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.HIT_POINTS)
        .changeKey(ItemKey.toItemKey("no.rules.item"))
        .modifier(1)
        .build();
    ChangeRequest changeRequest = changeRequestService.submitChangeRequest(newChar.getId(), change);
    assertThat(changeRequest.getStatus()).isEqualTo(ChangeStatus.REJECTED);
    assertThat(changeRequest.getStatusLabel()).isEqualTo(ChangeStatusLabel.NO_RULES_FIRED);
  }

}
