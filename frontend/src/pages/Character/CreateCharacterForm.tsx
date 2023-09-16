import useCharacterService from "../../services/character.service";
import { useForm } from "react-hook-form";
import { useCallback, useEffect, useState } from "react";
import { Character } from "../../types/character";
import classes from "./AddCharacter.module.css";
import { useRaceService } from "../../services/race.service";
import { Race } from "../../types/race";

interface IProps {
  fetchCharactersHandler: any;
  onConfirm: any;
}

interface FormData {
  characterName: string;
  raceName: string;
  ageGroup: string;
  hero: boolean;

}

export const CreateCharacterForm = ({ fetchCharactersHandler, onConfirm }: IProps) => {

  const { createCharacter } = useCharacterService();
  const { getRaces } = useRaceService();

  const { getValues, register, formState: { errors }, handleSubmit, reset } = useForm<FormData>();
  const [races, setRaces] = useState<Race[]>([]);

  const [charData, setCharData] = useState<Character>({
    name: "",
    ageGroup: "MATURE",
    race: { name: "human" },
    hero: false
  });

  const fetchRacesHandler = useCallback(async () => {
      await getRaces()
      .then((races) => {
        localStorage.setItem("races", JSON.stringify(races));
      })
      .catch((e) => alert("Error fetching skills: " + e));
  }, [getRaces]);

  useEffect(() => {
    let raceJSON = localStorage.getItem("races");
    if(raceJSON !== null) {
      setRaces(prevRaces => raceJSON === null ? null : JSON.parse(raceJSON));
    } else {
      fetchRacesHandler().then(() => setRaces(prevRaces => raceJSON === null ? null : JSON.parse(raceJSON)));
    }
  }, [fetchRacesHandler]);

  const submitHandler = useCallback(async () => {
    const charPostData: Character = {
      name: getValues('characterName'),
      ageGroup: getValues('ageGroup'),
      race: { name: getValues('raceName') },
      hero: getValues('hero')
    };
    await createCharacter(charPostData);
    setCharData({ name: "", ageGroup: "MATURE", race: { name: "human" }, hero: false });
    fetchCharactersHandler();
    reset();
    onConfirm();
  }, [createCharacter, fetchCharactersHandler, getValues, onConfirm, reset]); //does this work? it was empty before

  const onSubmit = handleSubmit(submitHandler);
  const ageGroups = [{key: 1 , value: "YOUNG"}, {key: 2 , value: "MATURE"}, {key: 3 , value: "OLD"}];

  return (
    <form onSubmit={onSubmit}>
      <div>
        <label htmlFor="characterName">Character Name:</label>
        <input {...register("characterName", { required: true })} />
        {errors.characterName?.type === 'required' && <div className="error">You character must have a name</div>}
      </div>
      <div>
        <label htmlFor="hero">Is this a Hero?</label>
        <input type="checkbox" {...register("hero")} />
      </div>
      <div>
        <label>Age group</label>
        <select {...register("ageGroup")} defaultValue={"MATURE"}>
          {ageGroups.map(ageGroup => ( <option key={ageGroup.key} value={ageGroup.value} >{ageGroup.value}</option>))}
        </select>
      </div>
      <div>
        <label htmlFor="raceName">Race Name:</label>
        <select {...register("raceName")}>
          <option value="0"> -- Select a race -- </option>
          {races.map((race) => <option key={race.id} value={race.id}>{race.name}</option>)}
        </select>
      </div>
      <footer className={classes.actions}>
        <button type='submit'>Create</button>
        <button onClick={onConfirm}>Cancel</button>
      </footer>
    </form>
  );
};