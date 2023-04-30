import { Character } from "../../types/character";
import { Link } from "react-router-dom";
import { useCallback } from "react";
import { CharacterService } from "../../services/character.service";
import { ChangeService } from "../../services/change.service";
import { Change } from "../../types/change";
import { CharacterState } from "../../types/character-state";

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
  }, [character, fetchCharactersHandler]);

  const activateCharHandler = useCallback(async () => {
    const changeData: Change = {
      changeKey: "READY_TO_PLAY",
      changeType: "CHARACTER_READY_TO_PLAY",
      changeDescription: "Character {character.id} is ready to play",
      modifier: 0,
    };
    console.log("Activating character: " + character.id);
    if(character.id != null) {
      await ChangeService.doChange(character.id, changeData);
    }
  }, [character]);

  if( character.state == null || character.baseSkillPoints == null) {
    return <><li>Invalid Character</li></>
  }

  const canActivate : boolean = (character.baseSkillPoints <= 5 && character.state === CharacterState.INIT_COMPLETE);

  return (
    <li>
      <Link to={"/characters/" + character.id}>
        {character.name}
      </Link>
      <p>Race: {character.race.name}</p>
      <p>Hero: {character.hero.toString()}</p>
      <p>State: {character.state}</p>
      <button onClick={activateCharHandler} hidden={!canActivate} >activate</button>
      <button onClick={deleteCharHandler}>delete</button>
    </li>
  );
};
