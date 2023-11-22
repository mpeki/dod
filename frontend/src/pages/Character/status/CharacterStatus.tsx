import { BaseTraitValue, Character } from "../../../types/character";
import { Box } from "@mui/material";
import { StatusLinearProgress } from "./StatusLinearProgress";

interface IProps {
  character: Character;
}

export const CharacterStatus = ({ character }: IProps) => {

  if(! character.bodyParts ) {
    return null;
  }

  const currentTotalKP : number = character.bodyParts["TOTAL"].currentHP;
  const maxTotalKP : number = character.bodyParts["TOTAL"].maxHP;
  const baseTraitMap: Map<string, BaseTraitValue> = new Map(Object.entries(character.baseTraits ?? {}));
  const maxPsychePoints = baseTraitMap.get('PSYCHE')?.currentValue || 0;
  const currentPsychePoints = baseTraitMap.get('PSYCHE')?.currentValue || 0;
  const currentWeightCarried = character.weightCarried || 0;
  const maxWeightAllowed = baseTraitMap.get("STRENGTH")?.currentValue || 1;

  return (
    <Box width={112} paddingLeft={1}>
      <StatusLinearProgress label="Total Hit Points" maxValue={maxTotalKP} currentValue={currentTotalKP} yellowThreshold={maxTotalKP - 3} redThreshold={3} />
      <StatusLinearProgress label={"Psyche"} maxValue={maxPsychePoints} currentValue={currentPsychePoints} yellowThreshold={maxPsychePoints - 3} redThreshold={2}/>
      <StatusLinearProgress label={"Sanity"} maxValue={maxPsychePoints * 2} currentValue={currentPsychePoints * 2} yellowThreshold={maxPsychePoints - 10} redThreshold={6}/>
      <StatusLinearProgress label={"BEP Capacity"} maxValue={maxWeightAllowed} currentValue={currentWeightCarried} yellowThreshold={maxWeightAllowed - (maxWeightAllowed * .2)} redThreshold={ maxWeightAllowed - 3} goodDirection={"empty"}/>
    </Box>
  );
};