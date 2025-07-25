package dk.dodgame.domain.action.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import dk.dodgame.data.DODFact;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "actor" })
public class Action implements DODFact {
	Type type;
	Difficulty difficulty;
	Actor actor;
	ActionResult actionResult;
	String resultDescription;
	Action reaction;
	@Builder.Default
	boolean resolved = false;
	String turnReference;
}
