import { Character } from "../../types/character";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { CharacterState } from "../../types/character-state";
import {
  Avatar,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardHeader,
  Fab,
  Grid,
  Typography
} from "@mui/material";
import StartIcon from "@mui/icons-material/Start";
import CharacterContext from "./CharacterContext";
import withFlashing from "../../components/withFlashing";
import { useTranslation } from "react-i18next";
import { DeleteCharacterButton } from "../../components/Character/DeleteCharacterButton";

interface IProps {
  character: Character;
}


export const CharacterCard = ({ character }: IProps) => {

  // const { deleteCharacter } = useCharacterService();
  const charContext = useContext(CharacterContext);
  const FlashingActivateButton = withFlashing(Fab);
  const { t } = useTranslation(["races","char"]);

  if (!charContext) {
    throw new Error("CharacterCard must be rendered within an ActivateCharContext.Provider");
  }

  const { activateCharHandler } = charContext;
  const handleActivation = () => {
    const characterId = character.id ? character.id : "";
    activateCharHandler(characterId).then();
  };

  if (character.state == null || character.baseSkillPoints == null) {
    return <>
      <li>Invalid Character</li>
    </>;
  }

  const canActivate: boolean = (character.baseSkillPoints < 10 && character.state === CharacterState.INIT_COMPLETE);
  return (
    <Grid item xs={2} sm={3} md={3} padding={.2}>
      <Card elevation={5} variant="elevation" square={false} sx={{ minWidth: 200, minHeight: 220 }}>
        <CardActionArea component={Link} to={"/characters/" + character.id}>
          <CardHeader
            avatar={
              <Avatar aria-label="recipe">
                {character.name.substring(0, 1).toUpperCase()}
              </Avatar>
            }
            title={`${character.name.substring(0, 15)}${character.name.length > 15 ? "..." : ""}`}
            subheader={t(`races:${character.race.name}`)}
          />
          <CardContent>
            <Typography variant="body2" color="text.secondary">
              Hero: {character.hero.toString()} <br />
              Base Skill Points left: {character.baseSkillPoints} <br />
              State: {t(`char:state.${character.state}`)}
            </Typography>
          </CardContent>
        </CardActionArea>
        <CardActions disableSpacing>
          {canActivate && (
            <FlashingActivateButton onClick={handleActivation} aria-label="activate" size={"small"} color={"success"}
                                    sx={{ mr: 1 }} title="Activate">
              <StartIcon />
            </FlashingActivateButton>
          )}
          <DeleteCharacterButton characterId={character.id} inGutter={false}/>
        </CardActions>
      </Card>
    </Grid>
  );
};
