import { Container, Paper, Typography } from "@mui/material";
import logo from "../img/dod-logo.png";
import Stack from "@mui/material/Stack";
import { HomeImageMenu } from "../UI/HomeImageMenu";

export const Home = (): JSX.Element =>
  <Container disableGutters>
    <Paper elevation={3}>
      <Stack direction={"row"}>
        <Stack direction={"column"}>
          <Typography sx={{ p: 2 }} variant="h5" gutterBottom align={"justify"}>
            Welcome to Drager & Dæmoner
          </Typography>
          <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
            This is the Home page! Where you can find the latest news and updates about the game. You can also find links to the other pages of the game.
            Don't forget to check out the <a href="">bla bla bal</a>
          </Typography>
          <HomeImageMenu />
        </Stack>
      </Stack>
    </Paper>
  </Container>;
