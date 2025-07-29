package dk.dodgame.domain.action.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import dk.dodgame.data.CharacterItemDTO;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class MeleeWeaponAction extends FightAction {
	private CharacterItemDTO meleeWeapon;
	private int fv;
}
