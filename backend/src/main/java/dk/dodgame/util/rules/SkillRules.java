package dk.dodgame.util.rules;

import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.action.model.ActionResult;
import dk.dodgame.domain.action.model.Difficulty;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.system.rule.DoDRule;
import dk.dodgame.util.Dice;

import static java.lang.Math.ceil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SkillRules {

    private SkillRules(){}

    @DoDRule(name = "test skill - fv", type = RuleType.SKILL_RULE)
    public static ActionResult testSkill(int fv, int roll, Difficulty difficulty) {
        int modifiedFv = (int) ceil(fv * difficulty.getValue());
        if (modifiedFv < roll) {
            if (roll == 20 && Dice.roll("1t20") > modifiedFv) {
                return ActionResult.FUMBLE;
            }
            return ActionResult.FAILURE;
        } else {
            if (roll == 1 && Dice.roll("1t20") < modifiedFv) {
                return ActionResult.PERFECT;
            } else if (roll <= ((int) ceil((double) modifiedFv / 3))) {
                return ActionResult.MASTERFUL;
            }
            return ActionResult.SUCCESS;
        }
    }
    @DoDRule(name = "test skill - skillDTO", type = RuleType.SKILL_RULE)
    public static ActionResult testSkill(CharacterSkillDTO charSkill, int roll, Difficulty difficulty) {
        SkillDTO skill = charSkill.getSkill();
        int fv = skill.getCategory() == Category.A ? charSkill.getFv() : getSkillCatBFV(charSkill.getFv());
		ActionResult result = testSkill(fv, roll, difficulty);
		log.info("Testing skill: {}, fv: {}, roll: {}, difficulty: {}, result: {}",
				skill.getKey().getKeyValue(), charSkill.getFv(), roll, difficulty, result);

        return result;
    }

    private static int getSkillCatBFV(int fv) {
        switch (fv) {
            case 1 -> fv = 13;
            case 2 -> fv = 15;
            case 3 -> fv = 17;
            case 4 -> fv = 19;
            case 5 -> fv = 20;
            default -> fv = 20 + (fv - 5) * 2;
        }
        return fv;
    }

}
