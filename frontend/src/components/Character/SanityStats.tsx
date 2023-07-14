import { BaseTraitValue, Character } from "../../types/character";
import { List, ListItem, ListItemText } from "@mui/material";
import { StyledList } from "../shared/List.styled";

interface ICharacterProps {
  character: Character;
}

export const SanityStats = ({ character }: ICharacterProps) => {

  const baseTraitMap: Map<string, BaseTraitValue> = new Map(Object.entries(character.baseTraits ?? {}));
  const maxPsykePoints = baseTraitMap.get('PSYCHE')?.currentValue || 0;
  const currentPsykePoints = baseTraitMap.get('PSYCHE')?.currentValue || 0;

  return (
    <StyledList>
      <List dense >
        <ListItem>
          <ListItemText primary="Sanity Points" secondary={`${currentPsykePoints * 2} (${maxPsykePoints * 2})`} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Psyke Points" secondary={`${currentPsykePoints} (${maxPsykePoints})`} />
        </ListItem>
      </List>
    </StyledList>
  );
};