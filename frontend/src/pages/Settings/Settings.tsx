import { Slider, Switch, Typography } from "@mui/material";

export const Settings = () => {
  const label = { inputProps: { 'aria-label': 'Switch demo' } };
  return (
    <>
      <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
        Settings
      </Typography>
      <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
        There really aren't any settings to play with yet, but since you logged in an all.
      </Typography>
      <Slider
        size="small"
        defaultValue={70}
        aria-label="Small"
        valueLabelDisplay="auto"
      />
      <Slider defaultValue={50} aria-label="Default" valueLabelDisplay="auto" />
      <Switch {...label} defaultChecked />
      <Switch {...label} />
      <Switch {...label} disabled defaultChecked />
      <Switch {...label} disabled />
    </>
  );
};