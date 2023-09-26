import { Character } from "../../types/character";
import { List, ListItem, ListItemText, Stack } from "@mui/material";
import { StyledList } from "../shared/List.styled";
import EditableListItem from "../../UI/EditableListItem";
import { useTranslation } from "react-i18next";

interface ICharacterProps {
  character: Character;
  username: string;
  changeHandler: (changeKey: string, mod: any) => void;
}

export const CharacterInfo = ({ character, username, changeHandler }: ICharacterProps) => {

  const { t } = useTranslation(["races","char"]);

  if(character == null || character.id == null || character.looks == null) {
    return <><p>Invalid character!</p></>;
  }

  return (
    <>
      <StyledList style={{ margin: '2px', marginTop: '15px' }}>
        <List dense={false} disablePadding={true}>
          <EditableListItem changeType="NAME"
                            label={t("char:detail.info.name")}
                            value={character.name}
                            changeHandler={changeHandler}
                            allowEdit={character.state === "INIT_COMPLETE"}
                            validationPattern={/^[\p{L}]{2}([- ]?[\p{L}])*$/u}
                            validationErrorMsg={"Invalid name! Must be at least 2 characters long and contain only letters and spaces."}

          />
          <ListItem>
            <ListItemText primary={t("char:detail.info.player")} secondary={username} />
          </ListItem>
          <ListItem>
            <ListItemText primary={t("char:detail.info.race")} secondary={t(`races:${character.race.name}`)} />
          </ListItem>
          <ListItem>
            <ListItemText primary={t("char:detail.info.socialStatus.title")} secondary={t(`char:detail.info.socialStatus.${character.socialStatus}`)} />
          </ListItem>
          <ListItem>
            <ListItemText primary={t("char:detail.info.profession")} secondary="-" />
          </ListItem>
          <ListItem>
            <ListItemText primary={t("char:detail.info.age.title")} secondary={t(`char:detail.info.age.${character.ageGroup}`)} />
          </ListItem>
          <ListItem>
            <ListItemText primary={t("char:detail.info.looks")}
                          secondary={`Has ${character.looks.hairLength} ${character.looks.hairColor} hair and ${character.looks.eyeColor} eyes, a ${character.looks.voice} voice, and a ${character.looks?.beardLength}.`} />
          </ListItem>
          <Stack direction={'row'}>
            <EditableListItem label={t("char:detail.info.height")}
                              value={character.looks.height}
                              changeType="HEIGHT"
                              changeHandler={changeHandler}
                              allowEdit={character.state === "INIT_COMPLETE"}
                              validationPattern={/^\d{2,3}$/}
                              validationErrorMsg={"Invalid height!"}
            />
            <EditableListItem label={t("char:detail.info.weight")}
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
            <ListItemText primary={t("char:detail.info.goals")} secondary="-" />
          </ListItem>
        </List>
      </StyledList>
    </>
  );
};