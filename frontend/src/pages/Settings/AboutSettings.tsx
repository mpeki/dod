import { Grid } from "@mui/material";
import { About } from "./About";
import { Settings } from "./Settings";
import settingsImg from "../../img/settings.png";
import { useAuth } from "react-oidc-context";

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

export const AboutSettings = () => {

  const auth = useAuth();

  return (
    <Grid container style={styles.settingsContainer}>
      <Grid item style={styles.imageContainer}></Grid>
      <Grid item style={styles.textContainer}>
        <About />
        {auth.isAuthenticated && <Settings />}
      </Grid>
    </Grid>
  );
};
