package dk.dodgame.domain.action.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "target" })
@ToString(exclude = { "target" })
public class MeleeWeaponAttackAction extends MeleeWeaponAction {
    Actor target;
}
