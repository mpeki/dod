import { useNavigate, useParams } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";
import { Character } from "../../types/character";
import useCharacterService from "../../services/character.service";
import { BaseTraitList } from "../../components/BaseTraits/BaseTraitList";
import { CharacterInfo } from "../../components/Character/CharacterInfo";
import { Container, Divider, Paper } from "@mui/material";
import { Masonry } from "@mui/lab";
import { SkillContainer } from "../Skill/SkillContainer";
import { HeroStats } from "../../components/Character/HeroStats";
import { SanityStats } from "../../components/Character/SanityStats";
import { BodyContainer } from "./BodyContainer";
import { WeaponsContainer } from "../Items/WeaponsContainer";
import { ItemsContainer } from "../Items/ItemsContainer";
import { FundsContainer } from "../Items/FundsContainer";
import { useChangeService } from "../../services/change.service";
import { Change } from "../../types/change";
import { MovementStats } from "../../components/Character/MovementStats";
import Stack from "@mui/material/Stack";
import { ReputationStats } from "../../components/Character/ReputationStats";
import { useAuth } from "react-oidc-context";
import { KeyboardShortcutProvider } from "../../components/KeyboardShortcutProvider";
import { showWarningSnackbar } from "../../utils/DODSnackbars";

export const ViewCharacter = () => {

  const auth = useAuth();
  const { getCharacter } = useCharacterService();
  const { doChange } = useChangeService();
  const { charId } = useParams();
  const [character, setCharacter] = useState<Character>();

  const [changeData, setChangeData] = useState<Change>({
    changeType: "",
    changeDescription: "",
    changeKey: "",
    modifier: ""
  });

  const fetchCharHandler = useCallback(async () => {
    getCharacter("" + charId)
    .then((character) => {
      setCharacter(character);
    })
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, [charId, getCharacter]);

  useEffect(() => {
    fetchCharHandler().then();
  }, [fetchCharHandler]);

  const navigate = useNavigate();
  const shortcuts = [{ key: "Escape", callback: () => navigate("/characters") },];

  const changeHandler = useCallback(async (changeKey: string, mod: any) => {
    const change: Change = {
      ...changeData,
      changeType: "CHARACTER_CHANGE_" + changeKey,
      changeDescription: "Changed " + changeKey + " to " + mod,
      changeKey: changeKey,
      modifier: mod
    };
    doChange("" + charId, change).then();
  }, [changeData, charId, doChange]);

  if (character == null || character.id == null) {
    return <><p>Invalid character!</p></>;
  } else {

    return (
      <KeyboardShortcutProvider shortcuts={shortcuts}>
        <Container maxWidth="lg">
          <Masonry columns={2} spacing={1}>
            <Paper elevation={3}>
              <BaseTraitList baseTraits={character?.baseTraits} />
            </Paper>
            <Paper elevation={3}>
              <CharacterInfo character={character} username={auth.user?.profile.name ? auth.user.profile.name : ""}
                             changeHandler={changeHandler} />
            </Paper>
            <Paper>
              <SkillContainer character={character} skills={character?.skills} fetchCharHandler={fetchCharHandler} />
            </Paper>
            {character.state === "READY_TO_PLAY" && (
              <Paper elevation={3}>
                <Stack direction={"row"}>
                  <SanityStats character={character} />
                  {character.hero && (
                    <>
                      <Divider orientation="vertical" flexItem />
                      <ReputationStats character={character} />
                      <Divider orientation="vertical" flexItem />
                      <HeroStats character={character} />
                    </>
                  )}
                  <Divider orientation="vertical" flexItem />
                  <MovementStats character={character} />
                </Stack>
              </Paper>
            )}
            <Paper elevation={3}>
              <BodyContainer parts={character.bodyParts} />
            </Paper>
            <Paper elevation={3}>
              <WeaponsContainer character={character} fetchCharHandler={fetchCharHandler} />
            </Paper>
            <Paper elevation={3}>
              {character.items && (
                <FundsContainer character={character} />
              )}
            </Paper>
            <Paper elevation={3}>
              <ItemsContainer character={character} />
            </Paper>
          </Masonry>
        </Container>
      </KeyboardShortcutProvider>
    );
  }

};
