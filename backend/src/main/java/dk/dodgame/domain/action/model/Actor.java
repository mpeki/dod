package dk.dodgame.domain.action.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.combat.Fighter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "actorType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CharacterDTO.class, name = "character"),
    @JsonSubTypes.Type(value = Fighter.class, name = "fighter")
})
public interface Actor {

	String getActorType();
}
