package dk.dodgame.domain.action.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.combat.Fighter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "actorType",
	visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CharacterDTO.class, name = "character"),
    @JsonSubTypes.Type(value = Fighter.class, name = "fighter")
})
public interface Actor {
	/**
	 * Jackson calls this method when serialising so the "actorType" field is
	 * written.  It is also used during deserialisation to populate the
	 * property if the concrete class does not declare a real field.
	 */
	String getActorType();

}
