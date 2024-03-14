package dk.dodgame.domain.skill;

import dk.dodgame.DodApplication;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.race.RaceService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("regression")
@SpringBootTest(classes = DodApplication.class)
class SkillServiceApplicationTest {

    @Autowired
    private SkillService service;

    @Autowired
    RaceService raceService;

    @ParameterizedTest
    @CsvSource({
            "'primary.weapon'",
            "'primary.weapon'",
            "'primary.weapon'",
            "'secondary.weapon'",
            "'skill.that.does.not.exist'",
            "'primary.weapon'"})
    void findSkill(String skillKey) {
        if (skillKey.equals("skill.that.does.not.exist")) {
            assertThrows(SkillNotFoundException.class, () -> service.findSkillByKey(skillKey));
            SkillKey key = SkillKey.builder().value(skillKey).build();
            assertThrows(SkillNotFoundException.class, () -> service.findSkillByKey(key));
        } else {
            service.findSkillByKey(skillKey);
            service.findSkillByKey(SkillKey.builder().value(skillKey).build());
        }
    }

    @Test
    void findSkillByRace() {
        RaceDTO dwarf = raceService.getRaceByName("dwarf");
        List<SkillDTO> skills = service.findDeniedSkillsForRace(dwarf);
        assertFalse(skills.isEmpty());
    }

}
