import { Grid, Typography } from "@mui/material";
import settingsImg from "../../img/defaultError_goblin.png";


const styles = {
  settingsContainer: {
    width: "100%",
    height: "100%"
  },
  textContainer: {
    width: "75%"
    // Add any other styles specific to your text container here
  },
  imageContainer: {
    width: "25%",
    backgroundImage: `url(${settingsImg})`,
    backgroundPosition: "center",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat"
  }
};
export const DefaultError = () => {
  return (
    <Grid container style={styles.settingsContainer}>
      <Grid item style={styles.imageContainer}></Grid>
      <Grid item style={styles.textContainer}>
        <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
          Something went wrong!
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          But we are not sure what. Please try again later.
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          If you want to help us fix this, please describe the issue here: TBD.
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          We will do our best to fix it as soon as possible.
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          Thank you!
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          The Goblin Team
        </Typography>

      </Grid>
    </Grid>
  )
}