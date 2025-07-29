package dk.dodgame.domain.action.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionResultDescription {
	String actorName;
	String actionItemName;
	String actionResult;
	String difficulty;

	String targetName;
	String targetAreaName;

	public String getShortActionDescription() {
		return String.format("%s hits %s (%s)", actorName, targetName, actionResult);
	}
}
