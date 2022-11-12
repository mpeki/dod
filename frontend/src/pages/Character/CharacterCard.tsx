import { Character } from "../../types/character";
import { Link } from "react-router-dom";

export const CharacterCard = ({character}:{character: Character}): JSX.Element => {
  return (
    <li>
      <Link to={"/characters/" + character.id}>
        {character.name}
      </Link>
      <p>Race: {character.race.name}</p>
      <p>Hero: {character.hero.toString()}</p>
      <p>Age: {character.ageGroup}</p>
    </li>
  );
};
