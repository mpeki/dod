package dk.dodgame.domain.action.model;

import lombok.Getter;

@Getter
public enum Type {

	SKILL_TRAINING("training-action"),
	MELEE_WEAPON_ATTACK("melee-weapon-attack-action"),
	MELEE_WEAPON_PARRY("melee-weapon-parry-action"),
	MELEE_DODGE("melee-dodge-action");

	private final String value;

	Type(String value) {
		this.value = value;
	}

	public boolean isMeleeWeaponAction() {
		return this == MELEE_WEAPON_ATTACK || this == MELEE_WEAPON_PARRY;
	}

	public boolean isDefensiveAction() {
		return this == MELEE_DODGE || this == MELEE_WEAPON_PARRY;
	}

	public boolean isAttackAction() {
		return this == MELEE_WEAPON_ATTACK;
	}

}
