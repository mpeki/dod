import { LinearProgress, LinearProgressPropsColorOverrides, Tooltip, Typography } from "@mui/material";
import { Character } from "../../types/character";
import { OverridableStringUnion } from "@mui/types";
import { getColor, getMaxBPs, normalise, normaliseAsc } from "../../utils/BaseSkillPointHelper";
import { useTranslation } from "react-i18next";


interface IProps {
  character: Character;
  expected?: number;
  color?: OverridableStringUnion<
    "primary" | "secondary" | "error" | "info" | "success" | "warning" | "inherit",
    LinearProgressPropsColorOverrides
  >;
}

export const CharacterBasePoints = ({ character, color = "info", expected = -1 }: IProps) => {

  const { t } = useTranslation("skills");
  const maxBPs = getMaxBPs(character.ageGroup);
  const bpLeft: number = character.baseSkillPoints || 0;
  const variant = expected === -1 ? "determinate" : "buffer";
  const normalisedValue = variant === "determinate" ? normalise(bpLeft, maxBPs) : normaliseAsc(bpLeft, maxBPs);

  return (
    <>
      {variant === "buffer" && <Typography variant={"caption"}>{t("buySkillsForm.usageCaption", {used: maxBPs - bpLeft, price: expected, rest: bpLeft - expected})}</Typography>}
      <Tooltip title={`Base Skill Points Used: ${maxBPs - bpLeft} / ${maxBPs}`} arrow>
        <LinearProgress variant={variant} value={normalisedValue}
                        valueBuffer={normalisedValue + (expected / maxBPs * 100)}
                        sx={variant === "determinate" ? {
                          "& .MuiLinearProgress-bar": {
                            backgroundColor: getColor(bpLeft, normalisedValue)
                          }
                        } : {}} />
      </Tooltip>
    </>
  );
};

