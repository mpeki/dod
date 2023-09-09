import React, { useEffect, useState } from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import { ThemeProvider } from "styled-components";
import { useTheme } from "./Theme/useTheme";
import { GlobalStyles } from "./App.styled";
import { Backdrop, Box, LinearProgress } from "@mui/material";
import { AppRouter } from "./components/routing/AppRouter";
import { AppTabs } from "./components/routing/AppTabs";
import { Home } from "./pages/Home";
import { CharacterOverview } from "./pages/Character/CharacterOverview";
import { ViewCharacter } from "./pages/Character/ViewCharacter";
import { ViewItems } from "./pages/Items/ViewItems";
import { ViewSkill } from "./pages/Skill/ViewSkill";
import { useAuth } from "react-oidc-context";
import { useLoadAppDataWithRetry } from "./services/axios/useLoadAppDataWithRetry";

function App() {
  // const auth = useAuth();
  const { currentTheme, setActiveTheme } = useTheme();
  const [isSkillsLoaded, setIsSkillsLoaded] = useState(false);
  const { loading: loadingSkills, error: skillsLoadingError } = useLoadAppDataWithRetry("skills", "/skill");
  const [itemsEndpoint, setItemsEndpoint] = useState("/nocall");
  const [racesEndpoint, setRacesEndpoint] = useState("/nocall");
  useEffect(() => {
    if ( skillsLoadingError === null ) {
      setItemsEndpoint("/items");
    }
  }, [loadingSkills, skillsLoadingError]);

  const { loading: loadingItems, error: itemsLoadingError } = useLoadAppDataWithRetry("items", itemsEndpoint);

  useEffect(() => {
    if ( itemsLoadingError === null ) {
      setRacesEndpoint("/race");
    }
  }, [loadingItems, itemsLoadingError]);

  const { loading: loadingRaces, error: racesLoadingError } = useLoadAppDataWithRetry("races", racesEndpoint);


  return (
    <>
      {currentTheme && (
        <ThemeProvider theme={currentTheme}>
          <Backdrop
            sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer -1 }}
            open={ loadingSkills || loadingItems || loadingRaces }
          >
            <Box position="fixed" top={0} left={0} right={0}>
              <LinearProgress />
            </Box>
          </Backdrop>
          <GlobalStyles />
          <AppRouter>
            <Box sx={{ p: 10 }}>
              <AppTabs />
              <Routes>
                <Route index element={<Home />} />
                <Route path="/home" element={<Home />} />
                <Route path="/characters" element={<CharacterOverview />} />
                <Route path="/items" element={<ViewItems />} />
                <Route path="/skill/:skillKey" element={<ViewSkill />} />
                <Route path="/characters/:charId" element={<ViewCharacter />} />
              </Routes>
            </Box>
          </AppRouter>
        </ThemeProvider>
      )}
    </>
  );
}
export default App;
