import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Character } from "../../types/character";
import { CharacterService } from "../../services/character.service";
import { BaseTraitList } from "./BaseTraitList";
import { BodyContainer } from "./BodyContainer";

export const ViewCharacter = () => {
  const { charId } = useParams();
  const [character, setCharacter] = useState<Character>();

  useEffect(() => {
    CharacterService.getCharacter("" + charId)
    .then((character) => {
      setCharacter(character);
    })
    .catch((e) => alert("Error fetching character: " + e));
  }, [charId]);

    return (
      <>
        <h1>
          {character?.name}
        </h1>
        <BaseTraitList baseTraits={character?.baseTraits} />
        <BodyContainer parts={character?.bodyParts} />
      </>
    );

};
