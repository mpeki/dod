import { Character } from "../../types/character";
import { User } from "../../types/user";
import { List, ListItem, ListItemText, Stack } from "@mui/material";
import { StyledList } from "../shared/List.styled";
import EditableListItem from "../../UI/EditableListItem";

interface ICharacterProps {
  character: Character;
  user: User;
  changeHandler: (changeKey: string, mod: any) => void;
}

export const CharacterInfo = ({ character, user, changeHandler }: ICharacterProps) => {

  if(character == null || character.id == null || character.looks == null) {
    return <><p>Invalid character!</p></>;
  }

  return (
    <>
      <StyledList style={{ margin: '2px', marginTop: '15px' }}>
        <List dense={false} disablePadding={true}>
          <EditableListItem changeType="NAME"
                            label="Name"
                            value={character.name}
                            changeHandler={changeHandler}
                            allowEdit={character.state === "INIT_COMPLETE"}
                            validationPattern={/^[\p{L}]{2}([- ]?[\p{L}])*$/u}
                            validationErrorMsg={"Invalid name! Must be at least 2 characters long and contain only letters and spaces."}

          />
          <ListItem>
            <ListItemText primary="Player Name" secondary={user.name} />
          </ListItem>
          <ListItem>
            <ListItemText primary="Race" secondary={character.race.name} />
          </ListItem>
          <ListItem>
            <ListItemText primary="Social Status" secondary={character.socialStatus} />
          </ListItem>
          <ListItem>
            <ListItemText primary="Profession" secondary="-" />
          </ListItem>
          <ListItem>
            <ListItemText primary="Age" secondary={character.ageGroup} />
          </ListItem>
          <ListItem>
            <ListItemText primary="Looks"
                          secondary={`Has ${character.looks.hairLength} ${character.looks.hairColor} hair and ${character.looks.eyeColor} eyes, a ${character.looks.voice} voice, and a ${character.looks?.beardLength}.`} />
          </ListItem>
          <Stack direction={'row'}>
            <EditableListItem label="Height"
                              value={character.looks.height}
                              changeType="HEIGHT"
                              changeHandler={changeHandler}
                              allowEdit={character.state === "INIT_COMPLETE"}
                              validationPattern={/^\d{2,3}$/}
                              validationErrorMsg={"Invalid height!"}
            />
            <EditableListItem label="Weight"
                              value={character.looks.weight}
                              changeType="WEIGHT"
                              changeHandler={changeHandler}
                              allowEdit={character.state === "INIT_COMPLETE"}
                              validationPattern={/^\d{2,3}$/}
                              validationErrorMsg={"Invalid weight!"}
            />
            <ListItem><ListItemText/></ListItem>
            <ListItem><ListItemText/></ListItem>
          </Stack>
          <ListItem>
            <ListItemText primary="Life Goals" secondary="-" />
          </ListItem>
        </List>
      </StyledList>
    </>
  );
};