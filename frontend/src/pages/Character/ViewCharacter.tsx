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
        <p><u>Race: </u>{character.race.name}</p>
        <p><u>Hero:</u> {character.hero.toString()}</p>
        <h3>Info: </h3>
        <p><u>Age: </u>{character?.ageGroup}</p>
        <p><u>Looks: </u></p>
        <div>Eyes: {character?.looks?.eyeColor}</div>
        <div>Voice: {character?.looks?.voice}</div>
        <div>Hair: {character?.looks?.hairColor}, length: {character?.looks?.hairLength}</div>
        <div>beard: {character?.looks?.beardLength}</div>

        <p><u>Favorite Hand: </u>{character?.favoriteHand}</p>
        <p><u>Social Status: </u>{character?.socialStatus}</p>
        <BaseTraitList baseTraits={character?.baseTraits} />
        <BodyContainer parts={character?.bodyParts} />
        <SkillContainer character={character} skills={character?.skills} fetchCharHandler={fetchCharHandler} />
      </>
    );
  }

};
