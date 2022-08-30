package dk.pekilidi.dod.character;

import dk.pekilidi.dod.rules.changes.ChangeKey;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BaseTraitName implements ChangeKey, Serializable {
    STRENGTH, SIZE, CONSTITUTION, DEXTERITY, INTELLIGENCE, PSYCHE, CHARISMA
}
