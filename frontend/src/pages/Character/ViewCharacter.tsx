import { useParams } from "react-router-dom";
import { useCallback, useContext, useEffect } from "react";
import { BaseTraitList } from "../../components/BaseTraits/BaseTraitList";
import { CharacterInfo } from "../../components/Character/CharacterInfo";
import { Container, Divider, Paper, Skeleton } from "@mui/material";
import { Masonry } from "@mui/lab";
import { SkillContainer } from "../Skill/SkillContainer";
import { HeroStats } from "../../components/Character/HeroStats";
import { SanityStats } from "../../components/Character/SanityStats";
import { BodyContainer } from "./BodyContainer";
import { WeaponsContainer } from "../Items/WeaponsContainer";
import { ItemsContainer } from "../Items/ItemsContainer";
import { FundsContainer } from "../Items/FundsContainer";
import { useChangeService } from "../../services/change.service";
import { Change, createChange } from "../../types/change";
import { MovementStats } from "../../components/Character/MovementStats";
import Stack from "@mui/material/Stack";
import { ReputationStats } from "../../components/Character/ReputationStats";
import { useAuth } from "react-oidc-context";
import CharacterContext from "./CharacterContext";
import { ErrorMain } from "../Error/ErrorMain";

export const ViewCharacter = () => {

  const auth = useAuth();

  const { doChange } = useChangeService();
  const { charId } = useParams();
  const charContext = useContext(CharacterContext);

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { fetchCharHandler, currentCharacter, errorCode } = charContext;

  useEffect(() => {
    fetchCharHandler(charId || "").then();
  }, [charId, fetchCharHandler]);

  const changeHandler = useCallback(async (changeKey: string, mod: any) => {
    const change: Change = createChange("CHARACTER_CHANGE_" + changeKey, "Changed " + changeKey + " to " + mod, changeKey, mod);
    doChange("" + charId, change).then();
  }, [charId, doChange]);

  if (currentCharacter == null || currentCharacter.id == null) {
    return (
      <Container maxWidth="lg" style={{ paddingTop: 25 }}>
        <Skeleton variant="rectangular" width="100%" height={100} />
      </Container>
    );
  } else if ( errorCode !== 0 ) {
    console.log('errorCode:', errorCode)
    return <ErrorMain errorCode={errorCode} />;
  } else {
    return (
      <>
        <Container maxWidth="lg" style={{ paddingTop: 25 }}>
          <div>
            <Masonry columns={2} spacing={1}>
              <Paper elevation={3}>
                <BaseTraitList baseTraits={currentCharacter?.baseTraits} />
              </Paper>
              <Paper elevation={3}>
                <CharacterInfo character={currentCharacter} username={auth.user?.profile.name ? auth.user.profile.name : ""}
                               changeHandler={changeHandler} />
              </Paper>
              <Paper>
                <SkillContainer character={currentCharacter} skills={currentCharacter?.skills} />
              </Paper>
              <Paper elevation={3}>
                <Stack direction={"row"}>
                  <SanityStats character={currentCharacter} />
                  {currentCharacter.hero && (
                    <>
                      <Divider orientation="vertical" flexItem />
                      <ReputationStats character={currentCharacter} />
                      <Divider orientation="vertical" flexItem />
                      <HeroStats character={currentCharacter} />
                    </>
                  )}
                  <Divider orientation="vertical" flexItem />
                  <MovementStats character={currentCharacter} />
                </Stack>
              </Paper>
              <Paper elevation={3}>
                <BodyContainer parts={currentCharacter.bodyParts} />
              </Paper>
              <Paper elevation={3} >
                <WeaponsContainer />
              </Paper>
              <Paper elevation={3}>
                {currentCharacter.items && (
                  <FundsContainer character={currentCharacter} />
                )}
              </Paper>
              <Paper elevation={3}>
                <ItemsContainer character={currentCharacter} />
              </Paper>
            </Masonry>
          </div>
        </Container>
      </>
    );
  }

};
