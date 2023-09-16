import { Typography } from "@mui/material";

export const About = () => {
  return (
    <>
      <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
        About
      </Typography>
      <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
        <p>Here you can find information about the game and the developers. There'll be links to the game's social media and other relevant sites.</p>
        <p>You can see what the current version of the game is and what the latest changes are.</p>
        <p>Lastly you'll be able to view what 3rd party software has been used to make this game.</p>
      </Typography>
    </>
  )
}