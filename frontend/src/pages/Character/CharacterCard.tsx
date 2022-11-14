import { Character } from "../../types/character";
import { Link } from "react-router-dom";
import { useCallback } from "react";
import { CharacterService } from "../../services/character.service";

interface IProps {
  character: Character;
  fetchCharactersHandler: any;
}
export const CharacterCard = ({character, fetchCharactersHandler}: IProps): JSX.Element => {

  const deleteCharHandler = useCallback(async () => {
    if (character.id != null) {
      await CharacterService.deleteCharacter(character.id)
      .then((characters) => {
        fetchCharactersHandler();
      })
      .catch((e) => alert("Error deleting character: " + e));
    }
  }, []);

  return (
    <li>
      <Link to={"/characters/" + character.id}>
        {character.name}
      </Link>
      <p>Race: {character.race.name}</p>
      <p>Hero: {character.hero.toString()}</p>
      <p>Age: {character.ageGroup}</p>
      <button onClick={deleteCharHandler}>delete</button>
    </li>
  );
};
