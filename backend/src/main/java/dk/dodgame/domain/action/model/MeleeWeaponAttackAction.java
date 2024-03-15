package dk.dodgame.domain.action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "target" })
public class MeleeWeaponAttackAction extends MeleeWeaponAction {
    Actor target;
}
