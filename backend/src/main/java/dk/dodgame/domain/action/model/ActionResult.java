package dk.dodgame.domain.action.model;

import dk.dodgame.util.ExcludeFromJacocoCoverage;

@ExcludeFromJacocoCoverage
public enum ActionResult {
    PERFECT, MASTERFUL, SUCCESS, FAILURE, FUMBLE, INVALID_ACTION;

	public boolean isSuccess() {
		return this == PERFECT || this == MASTERFUL || this == SUCCESS;
	}

	public boolean isFailure() {
		return this == FAILURE || this == FUMBLE || this == INVALID_ACTION;
	}
}
