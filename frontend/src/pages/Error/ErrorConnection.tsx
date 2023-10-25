import { Grid, Typography } from "@mui/material";
import settingsImg from "../../img/connectionError_goblin.png";
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
export const ErrorConnection = () => {
  return (
    <Grid container style={styles.settingsContainer}>
      <Grid item style={styles.imageContainer}></Grid>
      <Grid item style={styles.textContainer}>
        <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
          Where Does This Go?
        </Typography>
        <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
          It seems that we have lost our connection to the server. Probably a goblin stole the cable.<br/><br/>
          Sincerly Yours,<br/>
          The Goblin Engineering Team <br/>
        </Typography>
        <Spacer height="25em" />
      </Grid>
    </Grid>
  )
}