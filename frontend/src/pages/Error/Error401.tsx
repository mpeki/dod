import { Grid, Typography } from "@mui/material";
import settingsImg from "../../img/wizard401.png";
import { Spacer } from "../../components/Spacer";


const styles = {
  settingsContainer: {
    width: "100%",
    height: "100%"
  },
  textContainer: {
    width: "60%"
    // Add any other styles specific to your text container here
  },
  imageContainer: {
    width: "40%",
    backgroundImage: `url(${settingsImg})`,
    backgroundPosition: "center",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat"
  }
};
export const Error401 = () => {
  return (
    <Grid container style={styles.settingsContainer}>
      <Grid item style={styles.imageContainer}></Grid>
      <Grid item style={styles.textContainer}>
        <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
          None Shall Pass!
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          You are not authorized to view this page.
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          If you think this is an error, please login or contact the administrator.
        </Typography>
        <Spacer height="25em" />
      </Grid>
    </Grid>
  )
}