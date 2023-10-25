import { Character } from "../../../../types/character";
import {
  Grid,
  Table, TableBody,
  TextField
} from "@mui/material";
import { useTranslation } from "react-i18next";
import { StyledTable } from "../../../../components/shared/Table.styled";

interface ICharacterProps {
  character: Character;
  username: string;
  changeHandler: (changeKey: string, mod: any) => void;
}

export const PrintCharacterInfo = ({ character, username }: ICharacterProps) => {

  const inputTextSize = 14;
  const inputTextLabelSize = 13;

  const { t } = useTranslation(["races","char"]);

  if(character == null || character.id == null || character.looks == null) {
    return <><p>Invalid character!</p></>;
  }

  return (
    <>
      <Grid container spacing={2} >
        <Grid item xs={3}>
          <TextField label={t("char:detail.info.name")}
                     defaultValue={character.name}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size={"small"}/>
          <TextField label={t("char:detail.info.socialStatus.title")}
                     defaultValue={t(`char:detail.info.socialStatus.${character.socialStatus}`)}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
        </Grid>
        <Grid item xs={3}>
          <TextField label={t("char:detail.info.player")}
                     defaultValue={username}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
          <TextField label={t("char:detail.info.profession")}
                     defaultValue={"-"}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
        </Grid>
        <Grid item xs={2}>
          <TextField label={t("char:detail.info.race")}
                     defaultValue={t(`races:${character.race.name}`)}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
          <TextField label={t("char:detail.info.height")}
                     defaultValue={character.looks.height}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
        </Grid>
        <Grid item xs={2}>
          <TextField label={t("char:detail.info.age.title")}
                     defaultValue={t(`char:detail.info.age.${character.ageGroup}`)}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>
          <TextField label={t("char:detail.info.weight")}
                     defaultValue={character.looks.weight}
                     variant="standard"
                     inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
                     InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
                     size="small"/>

        </Grid>
      </Grid>
      <Grid container spacing={2}>
        <Grid item xs={11}>
          <TextField
            id="standard-multiline-static"
            label={t("char:detail.info.looks")}
            multiline
            rows={2}
            maxRows={2}
            defaultValue={`Has ${character.looks.hairLength} ${character.looks.hairColor} hair and ${character.looks.eyeColor} eyes, a ${character.looks.voice} voice, and a ${character.looks?.beardLength}.`}
            variant="standard"
            inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
            InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
            fullWidth={true}
          />
        </Grid>
        <Grid item xs={11}>
          <TextField
            id="standard-multiline-static"
            label={t("char:detail.info.goals")}
            multiline
            rows={2}
            maxRows={2}
            defaultValue="-"
            variant="standard"
            fullWidth={true}
            inputProps={{style: {fontSize: inputTextSize}}} // font size of input text
            InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
          />
        </Grid>
      </Grid>
    </>
  );
};