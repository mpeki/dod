import { Character } from "../../types/character";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { CharacterState } from "../../types/character-state";
import {
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardHeader,
  Fab,
  Grid,
  Stack,
  Tooltip,
  Typography
} from "@mui/material";
import StartIcon from "@mui/icons-material/Start";
import CharacterContext from "./CharacterContext";
import withFlashing from "../../components/withFlashing";
import { useTranslation } from "react-i18next";
import { DeleteCharacterButton } from "../../components/Character/DeleteCharacterButton";
import { CharacterBasePoints } from "./CharacterBasePoints";
import { StartingCoin } from "./StartingCoin";
import { CharacterAvatar } from "./CharacterAvatar";
import { getMaxBPs, normalise } from "../../utils/BaseSkillPointHelper";
import { HumanoidComponent } from "./body/HumanoidComponent";
import HelpIcon from "@mui/icons-material/Help";
import { CharacterStatus } from "./status/CharacterStatus";


interface IProps {
  character: Character;
}


export const CharacterCard = ({ character }: IProps) => {

  const styles = {
    card: {
      minWidth: 200,
      minHeight: 226,
      position: "relative" as "relative"
    },
    cardActions: {
      position: "absolute" as "absolute",
      bottom: 0,
      left: 0,
      padding: "8px",
      opacity: 1
    }
  };

  // const { deleteCharacter } = useCharacterService();
  const charContext = useContext(CharacterContext);
  const FlashingActivateButton = withFlashing(Fab);
  const { t } = useTranslation(["races", "char"]);

  if (!charContext) {
    throw new Error("CharacterCard must be rendered within an ActivateCharContext.Provider");
  }

  const { activateCharHandler } = charContext;
  const handleActivation = () => {
    const characterId = character.id ? character.id : "";
    activateCharHandler(characterId).then(() => localStorage.removeItem(`${character.id}_startCap`));
  };

  if (character.state == null || character.baseSkillPoints == null) {
    return <>
      <li>Invalid Character</li>
    </>;
  }
  const canActivate: boolean = (character.baseSkillPoints < 10 && character.state === CharacterState.INIT_COMPLETE);
  const maxBPs: number = getMaxBPs(character.ageGroup);
  const bpLeft: number = character.baseSkillPoints || 0;
  const opacity: number = (normalise(bpLeft, maxBPs) / 100);

  return (
    <Grid item xs={2} sm={3} md={3} padding={.2}>
      <Card elevation={5} variant="elevation" square={false} style={styles.card}
            sx={{ backgroundColor: `rgba(255, 255, 255, ${opacity < .4 ? .4 : opacity})` }}>
        <CardActionArea component={Link} to={"/characters/" + character.id}>
          <CardHeader
            avatar={
              <CharacterAvatar aria-label="recipe" avatarSrc={""} character={character} />
            }
            title={`${character.name.substring(0, 15)}${character.name.length > 15 ? "..." : ""}`}
            subheader={t(`races:${character.race.name}`)}
          />
          <CardContent style={{ maxWidth: "220px", maxHeight: "220px", padding: "0px" }}>
            {character.state === CharacterState.INIT_COMPLETE && (
              <Stack spacing={.5}>
                <Stack direction={"column"}>
                  <Typography variant="body2" width={180} paddingLeft={1} style={{ fontSize: 12 }}>
                    Skill Points
                  </Typography>
                  <CharacterBasePoints character={character} />
                  <StartingCoin character={character} />
                </Stack>
              </Stack>
            )}
            {character.state === CharacterState.READY_TO_PLAY && (
              <Stack direction={"row"} spacing={.5}>
                <CharacterStatus character={character} />
                <HumanoidComponent character={character} />
              </Stack>
            )}
          </CardContent>
        </CardActionArea>
        <CardActions disableSpacing style={styles.cardActions}>
          {canActivate && (
            <FlashingActivateButton onClick={handleActivation} aria-label="activate" size={"small"}
                                    color={"success"}
                                    sx={{ mr: 1 }} title={"Activate"}>
              <StartIcon />
            </FlashingActivateButton>
          )}
          <DeleteCharacterButton characterId={character.id} inGutter={false} />
          {character.state === CharacterState.INIT_COMPLETE && (
            <Tooltip title={"This Character is not ready to play yet. Buy skills and equipment to get started. Once you are below 10 skill points, the character may be activated for play."} arrow>
              <HelpIcon />
            </Tooltip>
          )}
        </CardActions>
      </Card>
    </Grid>
  );
};
