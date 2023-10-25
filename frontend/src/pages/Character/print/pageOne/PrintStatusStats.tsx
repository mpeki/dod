import { Character } from "../../../../types/character";
import { Grid, TextField } from "@mui/material";

interface IProps {
  character: Character;
}
export const PrintStatusStats = ({ character: Character }: IProps) => {
  const inputTextSize = 14;
  const inputTextLabelSize = 13;

  return (
    <>
      <Grid item xs={6}>
        <TextField
          id="standard-multiline-static"
          label="Rygte"
          multiline
          rows={2}
          defaultValue=""
          variant="standard"
          InputProps={{
            readOnly: true,
            style: {fontSize: inputTextSize}
          }}
          InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
        />
      </Grid>
      <Grid item xs={6}>
        <TextField
          id="standard-multiline-static"
          label="Status"
          multiline
          rows={2}
          defaultValue=""
          variant="standard"
          InputProps={{
            readOnly: true,
            style: {fontSize: inputTextSize}
          }}
          InputLabelProps={{style: {fontSize: inputTextLabelSize}}}
        />
      </Grid>
    </>
  );
}