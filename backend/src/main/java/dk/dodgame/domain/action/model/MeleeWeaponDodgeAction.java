package dk.dodgame.domain.action.model;

import dk.dodgame.data.CharacterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MeleeWeaponDodgeAction extends MeleeWeaponAction {
    CharacterDTO attacker;
}
