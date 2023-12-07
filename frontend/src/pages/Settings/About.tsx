import { List, ListItem, ListItemIcon, ListItemText, Typography } from "@mui/material";
import useGameInfoService from "../../services/gameInfo.service";
import { useEffect, useState } from "react";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import { useAuth } from "react-oidc-context";

export const About = () => {

  const auth = useAuth();

  const { getGameEngineInfo, getGameEngineHealth } = useGameInfoService();
  const [gameVersion, setGameVersion] = useState("");
  const [gameHealth, setGameHealth] = useState("?");
  const [loginHealth, setLoginHealth] = useState("?");

  useEffect(() => {
    getGameEngineInfo().then((info) => {
      setGameVersion(info.build.version);
    });
  }, [getGameEngineInfo]);

  useEffect(() => {
    const checkHealthStatus = () => {
      getGameEngineHealth().then((health) => {
        setGameHealth(health.status);
      }).catch((err) => {
        setGameHealth("DOWN")
      });
    };
    // Call the function immediately to check the status on component mount
    checkHealthStatus();
    const intervalId = setInterval(checkHealthStatus, 10000);
    // Clear the interval on component unmount
    return () => clearInterval(intervalId);
  }, [getGameEngineHealth]);

  useEffect(() => {
    const checkHealthStatus = () => {
      auth.querySessionStatus()
      .then(function(status) {
        setLoginHealth("UP"); // Set loginHealth to true when the query is successful
      })
      .catch(function(err) {
        if (err.message && err.message.includes("Failed to fetch")) {
          setLoginHealth("DOWN"); // Set loginHealth to false if the error message is 'Failed to fetch'
        } else {
          setLoginHealth("UP");
        }
      });
    };
    // Call the function immediately to check the status on component mount
    checkHealthStatus();
    const intervalId = setInterval(checkHealthStatus, 10000);
    // Clear the interval on component unmount
    return () => clearInterval(intervalId);
  }, [auth]);

  return (
    <>
      <Typography sx={{ p: 2 }} variant="h5" align={"justify"}>
        About
      </Typography>
      <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
        Here you can find information about the game and the developers. There'll be links to the game's social media
        and other relevant sites.
      </Typography>
      <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
        Game Status
      </Typography>
      <List dense={true} disablePadding={true} sx={{ width: "25%" }}>
        <ListItem>
          <ListItemText primary={"Login Server"} secondary={null} />
          <ListItemIcon>
            {loginHealth === "?" ? <HelpOutlineIcon /> : loginHealth === "UP" ? <CheckCircleOutlineIcon sx={{ color: "green" }} /> :
              <ErrorOutlineIcon sx={{ color: "red" }} />}
          </ListItemIcon>
        </ListItem>
        <ListItem>
          <ListItemText primary={"Game Engine"} secondary={null} />
          <ListItemIcon>
            {gameHealth === "?" ? <HelpOutlineIcon /> : gameHealth === "UP" ? <CheckCircleOutlineIcon sx={{ color: "green" }} /> :
              <ErrorOutlineIcon sx={{ color: "red" }} />}
          </ListItemIcon>
        </ListItem>
        <ListItem>
          <ListItemText primary={"Game Engine Version"} secondary={gameVersion} />
        </ListItem>
      </List>
      <Typography sx={{ p: 2 }} variant="body1" align={"justify"}>
        Lastly you'll be able to view what 3rd party software has been used to make this game.
      </Typography>
    </>
  );
};