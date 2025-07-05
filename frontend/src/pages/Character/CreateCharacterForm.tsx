import classes from "./CreateCharacterForm.module.css";
import useCharacterService from "../../services/character.service";
import {useForm} from "react-hook-form";
import {useCallback, useEffect, useState} from "react";
import {Character} from "../../types/character";
import {useRaceService} from "../../services/race.service";
import {Race} from "../../types/race";
import {showWarningSnackbar} from "../../utils/DODSnackbars";
import {Button, Checkbox, FormControlLabel, FormGroup, Grid, MenuItem, TextField} from "@mui/material";
import {useTranslation} from "react-i18next";
import Select from "@mui/material/Select";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";

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
  const { t } = useTranslation(["char", "races"]);


  const { getValues, register, formState: { errors }, handleSubmit, reset } = useForm<FormData>();
  const [races, setRaces] = useState<Race[]>([]);

  const [charData, setCharData] = useState<Character>({
    id: undefined,
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
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, [getRaces]);

  useEffect(() => {
    let raceJSON = localStorage.getItem("races");
    if (raceJSON !== null) {
      setRaces(prevRaces => raceJSON === null ? null : JSON.parse(raceJSON));
    } else {
      fetchRacesHandler().then(() => setRaces(prevRaces => raceJSON === null ? null : JSON.parse(raceJSON)));
    }
  }, [fetchRacesHandler]);

  const submitHandler = useCallback(async () => {
    const charPostData: Character = {
      id: undefined,
      name: getValues("characterName"),
      ageGroup: getValues("ageGroup"),
      race: { name: getValues("raceName") },
      hero: getValues("hero")
    };
    try {
      await createCharacter(charPostData).then(
        (character) => localStorage.setItem(`${character.id}_startCap`, JSON.stringify(character.items.silver.quantity || 0))
      );
    } catch (e) {
      showWarningSnackbar((e as Error).message);
    } finally {
      setCharData({ id: undefined, name: "", ageGroup: "MATURE", race: { name: "human" }, hero: false });
      fetchCharactersHandler();
      reset();
      onConfirm();
    }
  }, [createCharacter, fetchCharactersHandler, getValues, onConfirm, reset]); //does this work? it was empty before

  const onSubmit = handleSubmit(submitHandler);
  const ageGroups = [{ key: 1, value: "YOUNG" }, { key: 2, value: "MATURE" }, { key: 3, value: "OLD" }];
  return (
    <form onSubmit={onSubmit}>
      <Grid container spacing={1} pt={2} padding={2}>
        <Grid item>
          <TextField placeholder={t("new.name.label")} {...register("characterName", { required: true })} autoFocus
                     variant="standard" />
          {errors.characterName?.type === "required" && <div className="error">{t('errors.name.missing')}</div>}
        </Grid>
        <Grid item>
          <FormGroup>
            <FormControlLabel {...register("hero")} control={<Checkbox />} label={t("new.hero.label")}
                              labelPlacement={"start"} />
          </FormGroup>
        </Grid>
      </Grid>
      <Grid container spacing={1} pt={2} padding={2}>
        <Grid item>
          <FormControl variant="standard" sx={{ m: 1, minWidth: 140 }}>
            <InputLabel>{t("detail.info.age.title")}</InputLabel>
            <Select {...register("ageGroup")} defaultValue={"MATURE"}
                    label="Alder"
            >
              {ageGroups.map(ageGroup => (<MenuItem key={ageGroup.key}
                                                    value={ageGroup.value}>{t(`detail.info.age.${ageGroup.value}`)}</MenuItem>))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item padding={2}>
          <FormControl variant="standard" sx={{ m: 1, minWidth: 140 }}>
            <InputLabel>{t("new.race.label")}</InputLabel>
            <Select {...register("raceName", { required: true })} >
              {races.map((race) => <MenuItem key={race.name} value={race.name}>{t(`races:${race.name}`)}</MenuItem>)}
            </Select>
            {errors.raceName?.type === "required" && <div className="error">{t('errors.race.missing')}</div>}
          </FormControl>

        </Grid>
      </Grid>
      <footer className={classes.actions}>
        <Grid container spacing={1} padding={2} direction={"row"}>
          <Grid item>
            <Button type="submit" variant="contained" color="success">
              {t('new.button.ok')}
            </Button>
          </Grid>
          <Grid item>
            <Button color="secondary" onClick={onConfirm}>
              {t('new.button.cancel')}
            </Button>
          </Grid>
        </Grid>
      </footer>
    </form>
  );
};
