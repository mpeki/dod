import { Character } from "../../types/character";
import { List, ListItem, ListItemText } from "@mui/material";
import { StyledList } from "../shared/List.styled";

interface ICharacterProps {
  character: Character;
}

export const ReputationStats = ({ character }: ICharacterProps) => {

  return (
    <StyledList>
      <List>
        <ListItem>
          <ListItemText primary="Reputiation" secondary="0" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Status" secondary="0" />
        </ListItem>
      </List>
    </StyledList>
  );
};