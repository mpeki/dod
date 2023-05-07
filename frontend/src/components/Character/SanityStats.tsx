import { Character } from "../../types/character";
import { List, ListItem, ListItemText } from "@mui/material";
import { StyledList } from "../shared/List.styled";

interface ICharacterProps {
  character: Character;
}

export const SanityStats = ({ character }: ICharacterProps) => {

  return (
    <StyledList>
      <List>
        <ListItem>
          <ListItemText primary="Sanity Points" secondary="0" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Psyke Points" secondary="0" />
        </ListItem>
      </List>
    </StyledList>
  );
};