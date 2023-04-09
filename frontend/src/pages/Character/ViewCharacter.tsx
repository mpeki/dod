import { useParams } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";
import { Character } from "../../types/character";
import { CharacterService } from "../../services/character.service";
import { BaseTraitList } from "./BaseTraitList";
import { BodyContainer } from "./BodyContainer";
import { SkillContainer } from "../Skill/SkillContainer";

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

  if(character == null || character.id == null) {
    return <><p>Invalid character!</p></>
  } else {
    return (
      <>
        <h1>
          {character?.name}
        </h1>
        <BaseTraitList baseTraits={character?.baseTraits} />
        <BodyContainer parts={character?.bodyParts} />
        <SkillContainer charId={character.id} skills={character?.skills} fetchCharHandler={fetchCharHandler} />
      </>
    );
  }

};
