package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.pekilidi.dod.character.model.BaseTraitName;

@JsonDeserialize(as = BaseTraitName.class)
public interface ChangeKey {}
