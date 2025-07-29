package dk.dodgame.domain.action.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import dk.dodgame.data.DODFact;
import dk.dodgame.util.DoDTsidGenerator;

/**
 * Represents an action in the game domain. This class serves as a base model for actions
 * that can be performed during gameplay, including actions like skill training, combat,
 * and other game-related events.
 *
 * The `Action` class encapsulates core properties that define the nature of an action,
 * its difficulty, the actor performing the action, result of the action, and additional
 * related metadata. Subclasses can extend this base class to provide more specialized
 * behavior or attributes for specific types of actions.
 *
 * Key attributes:
 * - `ref`: A unique reference identifier for the action.
 * - `type`: The type of action, indicating the category or classification. See {@link dk.dodgame.domain.action.model.Type}.
 * - `difficulty`: The difficulty level of the action, which determines its complexity.
 * - `actor`: The entity performing the action, represented by an `Actor` interface.
 * - `actionResult`: The outcome of the action, described using `ActionResult` enum.
 * - `resultDescription`: A textual description of the action’s result.
 * - `reaction`: A possible follow-up action triggered as a reaction to this action.
 * - `resolved`: A boolean indicating whether the action has been resolved.
 * - `turnReference`: A reference to the game’s associated turn in which the action occurs.
 * - `turnSequence`: The sequence number of the action within a turn.
 *
 * This class makes use of Lombok annotations for reducing boilerplate code
 * (e.g., `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, etc.) and also
 * Builder pattern support for creating actions.
 *
 * Note: The `resolved` property helps in flagging whether the action reached
 * its conclusion, and `reaction` allows for chaining or triggering related follow-ups.
 */
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
	ActionResultDescription resultDescription;
	Action reaction;
	@Builder.Default
	boolean resolved = false;
	String turnReference;
	int turnSequence;
}
