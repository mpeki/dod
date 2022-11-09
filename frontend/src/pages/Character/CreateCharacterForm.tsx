import { CharacterService } from "../../services/character.service";
import { useForm } from "react-hook-form";
import { useCallback, useState } from "react";
import { Character } from "../../types/character";
import classes from "./AddCharacter.module.css";

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

  const { getValues, register, formState: { errors }, handleSubmit, reset } = useForm<FormData>();

  const [charData, setCharData] = useState<Character>({
    name: "",
    ageGroup: "MATURE",
    race: { name: "human" },
    hero: false
  });

  const submitHandler = useCallback(async () => {
    const charPostData: Character = {
      name: getValues('characterName'),
      ageGroup: getValues('ageGroup'),
      race: { name: getValues('raceName') },
      hero: getValues('hero')
    };
    await CharacterService.createCharacter(charPostData);
    setCharData({ name: "", ageGroup: "MATURE", race: { name: "human" }, hero: false });
    fetchCharactersHandler();
    reset();
    onConfirm();
  }, []);

  const onSubmit = handleSubmit(submitHandler);

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
        <select {...register("ageGroup")}>
          <option value="MATURE">Mature</option>
          <option value="YOUNG">Young</option>
          <option value="OLD">Old</option>
        </select>
      </div>
      <div>
        <label htmlFor="raceName">Race Name:</label>
        <input {...register("raceName", { required: true } )} />{errors.raceName?.type === 'required' && "Pick a race, any race!"}
      </div>
      <footer className={classes.actions}>
        <button type='submit'>Create</button>
        <button onClick={onConfirm}>Cancel</button>
      </footer>
    </form>
  );
};