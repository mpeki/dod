import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import LockOpenOutlinedIcon from "@mui/icons-material/LockOpenOutlined";
import { Tooltip } from "@mui/material";
import { showFatalConnectionErrorSnackbar } from "../../utils/DODSnackbars";
import { useTranslation } from "react-i18next";

export const AppTabs = () => {

  const auth = useAuth();
  const { t } = useTranslation("tabs");

  function useRouteMatch(patterns: readonly string[]) {
    const { pathname } = useLocation();
    for (let i = 0; i < patterns.length; i += 1) {
      const pattern = patterns[i];
      if (pathname.startsWith(pattern)) {
        return pattern;
      }
    }
    return null;
  }

  const handleLogin = async () => {
    try {
      await auth.signinRedirect();
    } catch (error) {
      console.log(error);
      showFatalConnectionErrorSnackbar("Sorry Can't Log You In Right Now", false);
    }
  };
  const handleLogout = async () => {
    try {
      await auth.removeUser();
      await auth.signoutRedirect();
    } catch (error) {
      console.log(error);
      showFatalConnectionErrorSnackbar("Sorry Can't Log You Out Right Now", false);
    }
  };

  const authHandler = async () => {
    if (auth.isAuthenticated) {
      handleLogout().then();
    } else {
      handleLogin().then();
    }
  };

  const currentTab = useRouteMatch(["/home", "/characters", "/city", "/wilderness", "/settings"]);

  return (
    <Tabs value={currentTab || "/home"} centered style={{ paddingBottom: 2}}>
      <Tab label={t("homeTab")} value="/home" to="/home" component={Link} />
      <Tab label={t("charactersTab")} value="/characters" to="/characters" component={Link}
           disabled={!auth.isAuthenticated} />
      <Tab label={t("cityTab")} value="/city" to="/city" component={Link} disabled={!auth.isAuthenticated} />
      <Tab label={t("wildernessTab")} value="/wilderness" to="/wilderness" component={Link}
           disabled={!auth.isAuthenticated} />
      <Tab label={auth.isAuthenticated ? t("settingsAboutTab") : t("aboutTab")} value="/settings" to="/settings"
           component={Link} />

      <Tab icon={auth.isAuthenticated ? <Tooltip title="Logout"><LockOutlinedIcon /></Tooltip> :
        <Tooltip title="Login"><LockOpenOutlinedIcon /></Tooltip>}
           iconPosition="end"
           onClick={() => void authHandler()}
      />
    </Tabs>
  );
};