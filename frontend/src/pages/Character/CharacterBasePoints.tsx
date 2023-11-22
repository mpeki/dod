import { LinearProgress, LinearProgressPropsColorOverrides, Tooltip } from "@mui/material";
import { Character } from "../../types/character";
import { OverridableStringUnion } from "@mui/types";
import { getColor, getMaxBPs, normalise } from "../../utils/BaseSkillPointHelper";


interface IProps {
  character: Character;
  color?: OverridableStringUnion<
    "primary" | "secondary" | "error" | "info" | "success" | "warning" | "inherit",
    LinearProgressPropsColorOverrides
  >;
}

export const CharacterBasePoints = ({ character, color = "info" }: IProps) => {

  const maxBPs = getMaxBPs(character.ageGroup);
  const bpLeft: number = character.baseSkillPoints || 0;
  const normalisedValue = normalise(bpLeft, maxBPs);

  return (
        <Tooltip title={`Base Skill Points Used: ${maxBPs - bpLeft} / ${maxBPs}`} arrow>
          <LinearProgress variant="determinate" value={normalisedValue}
            sx={{'& .MuiLinearProgress-bar': {
              backgroundColor: getColor(bpLeft, normalisedValue)
            }
          }} />
        </Tooltip>
  );
};

