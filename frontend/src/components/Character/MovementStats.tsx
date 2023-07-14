import { Character } from "../../types/character";
import { StyledList } from "../shared/List.styled";
import { List, ListItem, ListItemText } from "@mui/material";

interface ICharacterProps {
  character: Character;
}

export const MovementStats = ({ character }: ICharacterProps) => {
  return (
    <StyledList>
      <List dense>
        <ListItem>
          <ListItemText primary="Movement Points" secondary="L10 S05" />
        </ListItem>
      </List>
    </StyledList>
  );
}