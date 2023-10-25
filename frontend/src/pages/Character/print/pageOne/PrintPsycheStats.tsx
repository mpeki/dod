import { BaseTraitValue, Character } from "../../../../types/character";
import { Grid, TextField } from "@mui/material";

interface IProps {
  character: Character;
}

export const PrintPsycheStats = ({ character }: IProps) => {

  const baseTraitMap: Map<string, BaseTraitValue> = new Map(Object.entries(character.baseTraits ?? {}));
  const maxPsykePoints = baseTraitMap.get("PSYCHE")?.currentValue || 0;
  const inputTextSize = 14;
  const inputTextLabelSize = 13;


  return (
    <>
      <Grid item xs={6} >
        <TextField
          id="standard-multiline-static"
          label="Nervepoint"
          multiline
          rows={2}
          defaultValue={`(${maxPsykePoints * 2}):`}
          variant="standard"
          InputProps={{
            readOnly: true,
            style: { fontSize: inputTextSize }
          }}
          InputLabelProps={{ style: { fontSize: inputTextLabelSize } }}
        />
      </Grid>
      <Grid item xs={6}>
        <TextField
          id="standard-multiline-static"
          label="Psykepoint"
          multiline
          rows={2}
          defaultValue={`(${maxPsykePoints}):`}
          variant="standard"
          InputProps={{
            readOnly: true,
            style: { fontSize: inputTextSize }
          }}
          InputLabelProps={{ style: { fontSize: inputTextLabelSize } }}
        />
      </Grid>
    </>

  );
};