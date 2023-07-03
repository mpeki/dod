import { Container, Paper, Typography } from "@mui/material";
import Grid from "@mui/system/Unstable_Grid";

export const Home = (): JSX.Element =>
  <Container >
    <Paper elevation={3}>
      <Typography variant="body1" gutterBottom>
        This is the Home page! Where you can find the latest news and updates about the game.
      </Typography>
    </Paper>
  </Container>
  // <div>
  //   <h1>
  //     This is the Home page!
  //   </h1>
  // </div>
