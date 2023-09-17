import { Container, Paper, Typography } from "@mui/material";
import Stack from "@mui/material/Stack";
import { HomeImageMenu } from "../UI/HomeImageMenu";

const styles = {
  homeContainer: {
    width: "100%",
    maxWidth: 1200
  }
};

export const Home = () => {
  return (
    <Container style={styles.homeContainer} disableGutters>
      <Paper elevation={20}>
        <Stack direction={"row"}>
          <Stack direction={"column"}>
            <Typography sx={{ p: 2 }} variant="h5" gutterBottom align={"justify"}>
              Welcome to Drager & Dæmoner
            </Typography>
            <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
              This is the Home page! Where you can find the latest news and updates about the game. You can also find
              links to the other pages of the game.
            </Typography>
            <HomeImageMenu />
          </Stack>
        </Stack>
      </Paper>
    </Container>
  );
};
