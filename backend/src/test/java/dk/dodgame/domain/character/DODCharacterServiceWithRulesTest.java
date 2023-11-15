package dk.dodgame.domain.character;

import static dk.dodgame.util.BaseTestUtil.TEST_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.drools.model.PatternDSL.when;

import dk.dodgame.DodApplication;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.changerequest.ChangeRequestService;
import dk.dodgame.domain.changerequest.model.ChangeRequest;
import dk.dodgame.domain.changerequest.model.ChangeType;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.race.RaceNotFoundException;
import dk.dodgame.domain.race.RaceService;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.model.Group;
import dk.dodgame.data.SkillDTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class DODCharacterServiceWithRulesTest {

  @Autowired
  private CharacterService charService;
  @Autowired
  private RaceService raceService;
  @Autowired
  private ChangeRequestService changeService;

  @Test
  void createCharacterReturnChar() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotNull();
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isEqualTo(-1);
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createHeroReturnHero() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotNull();
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(7);
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isZero();
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createCharacterReturnCharWithFreeSkill() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotNull();
    org.assertj.core.api.Assertions.assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
    assertThat(newBeing.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isEqualTo(-1);
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createdCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    charService.createCharacter(testChar, TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(testChar.getBodyParts()).isNotEmpty();
  }

  @Test
  void foundCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    newBeing = charService.findCharacterByIdAndOwner(newBeing.getId(), TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(newBeing.getBodyParts()).isNotEmpty();
  }

  @Test
  void deletedCharacterIsGone() {
    CharacterDTO testChar = CharacterDTO.builder().name("nomore").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    newBeing = charService.findCharacterByIdAndOwner(newBeing.getId(), TEST_OWNER);
    org.assertj.core.api.Assertions.assertThat(newBeing.getBodyParts()).isNotEmpty();
    charService.deleteCharacterByIdAndOwner(newBeing.getId(), TEST_OWNER);
    Assertions.assertDoesNotThrow(() -> raceService.getRaceByName("human"));
    org.assertj.core.api.Assertions.assertThat(raceService.fetchRaces()).isNotEmpty();
  }

  @Test
  void getCharacterNonExistingRaceThrowsException() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("hobbit")).build();
    Assertions.assertThrows(RaceNotFoundException.class, () -> {
      charService.createCharacter(testChar, TEST_OWNER);
    });
  }

  @Test
  void possibleToFetchAllCharsAfterSkillBought() {
    CharacterDTO testChar = CharacterDTO.builder().name("New guy").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar, TEST_OWNER);
    List<CharacterDTO> chars = charService.fetchAllCharactersByOwner(TEST_OWNER);
    boolean foundNewGuy = false;
    for (CharacterDTO aChar : chars) {
      if (aChar.getName().equals(newBeing.getName())) {
        foundNewGuy = true;
      }
    }
    assertThat(foundNewGuy).isTrue();

    SkillDTO skill = SkillDTO
        .builder()
        .group(Group.COMBAT)
        .key(SkillKey.builder().value("primary.weapon").build())
        .build();

    ChangeRequest change = ChangeRequest
        .builder()
        .changeType(ChangeType.NEW_SKILL)
        .changeKey(skill.getKey())
        .modifier(13)
        .build();

    changeService.submitChangeRequest(newBeing.getId(), change, TEST_OWNER);

    charService.fetchAllCharactersByOwner(TEST_OWNER);
  }

  @Test
  void createManyCharacters() {
    List<String> characterIds = charService.createCharacters(10, "human", TEST_OWNER);
    for (String id : characterIds) {
      CharacterDTO charById = charService.findCharacterByIdAndOwner(id, TEST_OWNER);
      assertThat(charById).isNotNull();
      assertThat(charById.getRace().getName()).isEqualTo("human");
      org.assertj.core.api.Assertions.assertThat(charById.getBaseTraits()).isNotEmpty();
      assertThat(charById.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
      assertThat(charById.getBaseTraits().get(BaseTraitName.STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
      assertThat(charById.getHeroPoints()).isEqualTo(-1);
      assertThat(charById.getId()).isEqualTo(id);
    }
  }
}
