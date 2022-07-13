package dk.pekilidi.dod.rules.changes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.pekilidi.dod.character.BaseTraitName;

@JsonDeserialize(as = BaseTraitName.class)
public interface ChangeKey {}
