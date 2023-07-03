import { Character } from "../../types/character";
import { List, ListItem, ListItemText } from "@mui/material";
import { StyledList } from "../shared/List.styled";

interface ICharacterProps {
  character: Character;
}

export const HeroStats = ({ character }: ICharacterProps) => {

  return (
    <StyledList>
      <List>
        <ListItem>
          <ListItemText primary="Hero Points" secondary="0" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Hero Deeds" secondary="-" />
        </ListItem>
      </List>
    </StyledList>
  );
};