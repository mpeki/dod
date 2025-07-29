package dk.dodgame.domain.action.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import dk.dodgame.data.DODFact;
import dk.dodgame.util.DoDTsidGenerator;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "actor" })
public class Action implements DODFact {
	@Builder.Default
	private String ref = DoDTsidGenerator.createReferenceNo("act");
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
