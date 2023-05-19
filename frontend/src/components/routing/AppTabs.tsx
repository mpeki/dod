import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import { Link, useLocation } from "react-router-dom";

export const AppTabs = () => {

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

  const currentTab = useRouteMatch(['/home', '/characters', '/items']);

  return (
    <Tabs value={currentTab || "/home"} centered>
      <Tab label="Home" value="/home" to="/home" component={Link} />
      <Tab label="My Characters" value="/characters" to="/characters" component={Link} />
      <Tab label="The Store" value="/items" to="/items" component={Link} />
    </Tabs>
  );
}