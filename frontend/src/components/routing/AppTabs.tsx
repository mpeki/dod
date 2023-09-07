import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import LockOpenOutlinedIcon from '@mui/icons-material/LockOpenOutlined';
import { Tooltip } from "@mui/material";
import { showFatalConnectionErrorSnackbar } from "../../utils/DODSnackbars";

export const AppTabs = () => {

  const auth = useAuth();

  const handleLogout = () => {
    auth.removeUser();
    auth.signoutRedirect();
  };


  function useRouteMatch(patterns: readonly string[]) {
    const { pathname } = useLocation();
    for (let i = 0; i < patterns.length; i += 1) {
      const pattern = patterns[i];
      if(pathname.startsWith(pattern)) {
        return pattern;
      }
    }
    return null;
  }

  const handleLogin = async () => {
    try {
      await auth.signinRedirect();
    } catch (error) {
      console.log(error)
      showFatalConnectionErrorSnackbar("Sorry Can't Log You In Right Now", false);
    }
  }

  const currentTab = useRouteMatch(['/home', '/characters', '/items']);

  return (
    <Tabs value={currentTab || "/home"} centered>
      <Tab label="Home" value="/home" to="/home" component={Link} />
      <Tab label="My Characters" value="/characters" to="/characters" component={Link} disabled={!auth.isAuthenticated}/>
      <Tab label="The Store" value="/items" to="/items" component={Link} disabled={!auth.isAuthenticated}/>
      <Tab label="Profile" value="/account" to="/account" component={Link} disabled={!auth.isAuthenticated} />
      <Tab icon={auth.isAuthenticated ? <Tooltip title="Logout"><LockOutlinedIcon/></Tooltip> : <Tooltip title="Login"><LockOpenOutlinedIcon/></Tooltip>}
           iconPosition="end"
           onClick={auth.isAuthenticated ? () => void handleLogout() : () => void handleLogin()}
      />
    </Tabs>
  );
}