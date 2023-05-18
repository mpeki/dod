import { useParams } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";
import { Character } from "../../types/character";
import { CharacterService } from "../../services/character.service";
import { BaseTraitList } from "../../components/BaseTraits/BaseTraitList";
import { CharacterInfo } from "../../components/Character/CharacterInfo";
import { User } from "../../types/user";
import { Container, Paper, Box } from "@mui/material";
import { Masonry } from "@mui/lab";
import { SkillContainer } from "../Skill/SkillContainer";
import { ReputationStats } from "../../components/Character/ReputationStats";
import { HeroStats } from "../../components/Character/HeroStats";
import { SanityStats } from "../../components/Character/SanityStats";
import { BodyContainer } from "./BodyContainer";
import { WeaponsContainer } from "../items/WeaponsContainer";
import { ItemsContainer } from "../items/ItemsContainer";
import { FundsContainer } from "../items/FundsContainer";


export const ViewCharacter = () => {
  const { charId } = useParams();
  const [character, setCharacter] = useState<Character>();

  const fetchCharHandler = useCallback(async () => {
    CharacterService.getCharacter("" + charId)
    .then((character) => {
      setCharacter(character);
    })
    .catch((e) => alert("Error fetching character: " + e));
  }, []);

  useEffect(() => {
    fetchCharHandler().then();
  }, [fetchCharHandler]);
  if (character == null || character.id == null) {
    return <><p>Invalid character!</p></>;
  } else {

    //Remove this when user is implemented
    let user: User = {
      id: 0,
      name: "Test User"
    };

    return (
      <Container maxWidth="lg">
        <Masonry columns={2} spacing={1}>
          <Paper elevation={3}>
            <BaseTraitList baseTraits={character?.baseTraits} />
          </Paper>
          <Paper elevation={3}>
            <CharacterInfo character={character} user={user} />
          </Paper>
          <Paper>
            <SkillContainer character={character} skills={character?.skills} fetchCharHandler={fetchCharHandler} />
          </Paper>
          { character.state === "READY_TO_PLAY" && (
          <Paper elevation={3}>
            <SanityStats character={character}/>
            { character.hero && (
              <>
                <ReputationStats character={character}/>
                <HeroStats character={character}/>
              </>
            )}
          </Paper>
          )}
          <Paper elevation={3}>
            <BodyContainer parts={character.bodyParts}/>
          </Paper>
          <Paper elevation={3}>
            <WeaponsContainer />
          </Paper>
          <Paper elevation={3}>
            <FundsContainer />
          </Paper>
          <Paper elevation={3}>
            <ItemsContainer />
          </Paper>

        </Masonry>
      </Container>
    );
  }

};
