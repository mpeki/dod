import { Character } from "../../../../types/character";
import { Grid, TextField } from "@mui/material";
import { useTranslation } from "react-i18next";

interface IProps {
  character: Character;
}
export const PrintBonusXpHp = ({ character: Character }: IProps) => {
  const { t } = useTranslation("char");
  const inputTextSize = 14;
  const inputTextLabelSize = 13;

  return (
    <>
      <Grid item xs={5}>
        <TextField style={{ marginLeft: "10px" }}
                   id="standard-multiline-static"
                   label={t("detail.skills.bonusExp")}
                   multiline
                   rows={2}
                   defaultValue=""
                   variant="standard"
                   InputProps={{
                     readOnly: true,
                     style: { fontSize: inputTextSize }
                   }}
                   InputLabelProps={{ style: { fontSize: inputTextLabelSize } }}
        />
      </Grid>
      <Grid item xs={5}>
        <TextField id="standard-multiline-static"
                   label={"Heltepoint"}
                   multiline
                   rows={2}
                   defaultValue=""
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
}