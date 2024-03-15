package dk.dodgame.data.combat;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.DODFact;
import dk.dodgame.domain.action.model.Actor;
import dk.dodgame.util.DoDTsidGenerator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Fight implements DODFact {

    @Builder.Default
    private String ref = DoDTsidGenerator.createReferenceNo("fgt");
    Map<String, Fighter> fighters;
    @Builder.Default
    private int turnCounter = 0;
    @Builder.Default
    private FightState fightPhase = FightState.NEW;
    @Builder.Default
    private List<Turn> turns = new ArrayList<>();

    public List<? extends Actor> getListOfFighters() {
        return new ArrayList<>(fighters.values());
    }

	public List<CharacterDTO> getListCharacters() {
		return fighters.values().stream()
				.map(Fighter::getCharacter)
				.toList();
	}


	public Turn getCurrentTurn() {
        return turns.stream()
                .filter(Turn::isCurrent)
                .findFirst()
                .orElse(null);
    }
}
