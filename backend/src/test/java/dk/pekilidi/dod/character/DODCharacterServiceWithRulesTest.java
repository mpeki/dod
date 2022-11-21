package dk.pekilidi.dod.character;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.assertj.core.api.Assertions.assertThat;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.changerequest.ChangeRequestService;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.dod.race.RaceService;

import dk.pekilidi.dod.skill.SkillKey;
import dk.pekilidi.dod.skill.model.Group;
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
    CharacterDTO newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isEqualTo(-1);
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createHeroReturnHero() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    assertThat(newBeing.getBaseTraits()).isNotNull();
    assertThat(newBeing.getBaseTraits()).isNotEmpty();
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(7);
    assertThat(newBeing.getBaseTraits().get(STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
    assertThat(newBeing.getHeroPoints()).isZero();
    assertThat(newBeing.getRace().getName()).isEqualTo("human");
  }

  @Test
  void createdCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    charService.createCharacter(testChar);
    assertThat(testChar.getBodyParts()).isNotEmpty();
  }

  @Test
  void foundCharacterHasBody() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    newBeing = charService.findCharacterById(newBeing.getId());
    assertThat(newBeing.getBodyParts()).isNotEmpty();
  }

  @Test
  void deletedCharacterIsGone() {
    CharacterDTO testChar = CharacterDTO.builder().name("nomore").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    newBeing = charService.findCharacterById(newBeing.getId());
    assertThat(newBeing.getBodyParts()).isNotEmpty();
    charService.deleteCharacterById(newBeing.getId());
    Assertions.assertDoesNotThrow(() -> raceService.getRaceByName("human"));
    assertThat(raceService.fetchRaces().size()).isPositive();
  }

  @Test
  void getCharacterNonExistingRaceThrowsException() {
    CharacterDTO testChar = CharacterDTO.builder().name("bilbo").race(new RaceDTO("hobbit")).build();
    Assertions.assertThrows(RaceNotFoundException.class, () -> {
      charService.createCharacter(testChar);
    });
  }

  @Test
  void possibleToFetchAllCharsAfterSkillBought(){
    CharacterDTO testChar = CharacterDTO.builder().name("New guy").hero(true).race(new RaceDTO("human")).build();
    CharacterDTO newBeing = charService.createCharacter(testChar);
    List<CharacterDTO> chars = charService.fetchAllCharacters();
    boolean foundNewGuy = false;
    for (CharacterDTO aChar : chars) {
      if(aChar.getName().equals(newBeing.getName())){
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

    changeService.submitChangeRequest(newBeing.getId(), change);

    charService.fetchAllCharacters();
  }

  @Test
  void createManyCharacters(){
    List<String> characterIds = charService.createCharacters(10, "human");
    for (String id : characterIds) {
      CharacterDTO charById = charService.findCharacterById(id);
      assertThat(charById).isNotNull();
      assertThat(charById.getRace().getName()).isEqualTo("human");
      assertThat(charById.getBaseTraits()).isNotEmpty();
      assertThat(charById.getBaseTraits().get(STRENGTH).getCurrentValue()).isGreaterThanOrEqualTo(3);
      assertThat(charById.getBaseTraits().get(STRENGTH).getCurrentValue()).isLessThanOrEqualTo(18);
      assertThat(charById.getHeroPoints()).isEqualTo(-1);
      assertThat(charById.getId()).isEqualTo(id);
    }
  }
}
