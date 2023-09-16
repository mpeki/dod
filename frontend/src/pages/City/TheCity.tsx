import { Container, Paper, Typography } from "@mui/material";
import Stack from "@mui/material/Stack";
import { CityImageMenu } from "./CityImageMenu";

export const TheCity = () =>
  <Container disableGutters>
    <Paper elevation={3}>
      <Stack direction={"row"}>
        <Stack direction={"column"}>
          <Typography sx={{ p: 2 }} variant="h5" gutterBottom align={"justify"}>
            The City
          </Typography>
          <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
            As the aged, wooden gates creaked open, the adventurer was met with an overwhelming surge of sights, sounds, and scents unique to the bustling medieval city. The walls, while high and imposing from the outside, now seemed to embrace the vibrant tapestry of life they encircled.

            A dizzying array of stalls stood before him, each filled with an assortment of goods: colorful spices piled high like miniature mountains, fabrics of every conceivable hue and texture, and intricate trinkets that gleamed under the morning sun. Market sellers, each with their own distinct cry, shouted about the superiority of their wares, competing with one another in a chaotic yet rhythmic melody.

            Citizens from all walks of life weaved their paths through the streets: nobles adorned in velvet robes, mercenaries clinking in their chainmail, and jesters performing whimsical acts for a few coins. Children raced past, laughing as they played games, occasionally pausing to gaze at the newcomer with wide-eyed curiosity.

            Welcome to the city!
          </Typography>
          <CityImageMenu />
        </Stack>
      </Stack>
    </Paper>
  </Container>;
