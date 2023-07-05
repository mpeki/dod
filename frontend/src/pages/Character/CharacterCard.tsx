import { Character } from "../../types/character";
import { Link } from "react-router-dom";
import { useCallback } from "react";
import { CharacterService } from "../../services/character.service";
import { ChangeService } from "../../services/change.service";
import { Change } from "../../types/change";
import { CharacterState } from "../../types/character-state";
import {
  Avatar,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardHeader,
  Grid,
  IconButton,
  Typography
} from "@mui/material";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import StartIcon from "@mui/icons-material/Start";

interface IProps {
  character: Character;
  fetchCharactersHandler: any;
}

export const CharacterCard = ({ character, fetchCharactersHandler }: IProps): JSX.Element => {

  const deleteCharHandler = useCallback(async () => {
    if (character.id != null) {
      await CharacterService.deleteCharacter(character.id)
      .then((characters) => {
        fetchCharactersHandler();
      })
      .catch((e) => alert("Error deleting character: " + e));
    }
  }, [character, fetchCharactersHandler]);

  const activateCharHandler = useCallback(async () => {
    const changeData: Change = {
      changeKey: "READY_TO_PLAY",
      changeType: "CHARACTER_READY_TO_PLAY",
      changeDescription: "Character {character.id} is ready to play",
      modifier: 0
    };
    console.log("Activating character: " + character.id);
    if (character.id != null) {
      await ChangeService.doChange(character.id, changeData)
      .then((characters) => {
        fetchCharactersHandler();
      })
    }
  }, [character, fetchCharactersHandler]);

  if (character.state == null || character.baseSkillPoints == null) {
    return <>
      <li>Invalid Character</li>
    </>;
  }

  const canActivate: boolean = (character.baseSkillPoints < 10 && character.state === CharacterState.INIT_COMPLETE);

  return (
    <Grid item xs={2} sm={3} md={3}>
      <Card variant="outlined" sx={{ minWidth: 250, padding: 1 }}>
        <CardActionArea component={Link} to={"/characters/" + character.id}>
          <CardHeader
            avatar={
              <Avatar aria-label="recipe">
                {character.name.substring(0, 1).toUpperCase()}
              </Avatar>
            }
            title={`${character.name.substring(0, 15)}${character.name.length > 15 ? "..." : ""}`}
            subheader={character.race.name}
          />
          <CardContent>
            <Typography variant="body2" color="text.secondary">
              Hero: {character.hero.toString()} <br />
              Base Skill Points left: {character.baseSkillPoints} <br />
              State: {character.state}
            </Typography>
          </CardContent>
        </CardActionArea>
        <CardActions disableSpacing>
          {canActivate && (
            <IconButton onClick={activateCharHandler} aria-label="activate">
              <StartIcon />
            </IconButton>
          )}
          <IconButton onClick={deleteCharHandler} aria-label="delete" title="delete">
            <DeleteForeverIcon />
          </IconButton>
        </CardActions>
      </Card>
    </Grid>
  );
};
