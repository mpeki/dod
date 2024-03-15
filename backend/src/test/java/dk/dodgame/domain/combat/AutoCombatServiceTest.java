package dk.dodgame.domain.combat;

import dk.dodgame.DodApplication;
import dk.dodgame.data.combat.Fight;
import dk.dodgame.domain.character.CharacterService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class AutoCombatServiceTest {
    @Autowired
    private AutoCombatService service;
    @Autowired
    private CharacterService charService;

    @Test
    void testAutoFight() {




        Fight fight = Fight.builder().build();

        service.beginAutoFight(fight);

    }

}