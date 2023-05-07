import { Character } from "../../types/character";
import { User } from "../../types/user";
import { List, ListItem, ListItemText } from "@mui/material";
import { StyledList } from "../shared/List.styled";

interface ICharacterProps {
  character: Character;
  user: User;
}

export const CharacterInfo = ({ character, user }: ICharacterProps) => {


  return (
    <StyledList>
      <List dense={true} disablePadding={true}>
        <ListItem>
          <ListItemText primary="Name" secondary={character.name} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Player Name" secondary={user.name} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Race" secondary={character.race.name} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Social Status" secondary={character?.socialStatus} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Profession" secondary="-" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Age" secondary={character?.ageGroup} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Looks"
                        secondary={`Has ${character?.looks?.hairLength} ${character?.looks?.hairColor} hair and ${character?.looks?.eyeColor} eyes, a ${character?.looks?.voice} voice, and a ${character?.looks?.beardLength}.`} />
        </ListItem>
        <ListItem>
          <ListItemText primary="Height" secondary="-" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Weight" secondary="-" />
        </ListItem>
        <ListItem>
          <ListItemText primary="Life Goals" secondary="-" />
        </ListItem>
      </List>
    </StyledList>
  );
};