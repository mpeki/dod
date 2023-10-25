import React, { useEffect, useState } from "react";
import { Route, Routes, useLocation } from "react-router-dom";
import "./App.css";
import { ThemeProvider } from "styled-components";
import { useTheme } from "./Theme/useTheme";
import { GlobalStyles } from "./App.styled";
import { Backdrop, Box, Container, LinearProgress, Paper } from "@mui/material";
import { AppTabs } from "./components/routing/AppTabs";
import { Home } from "./pages/Home";
import { CharacterOverview } from "./pages/Character/CharacterOverview";
import { ViewCharacter } from "./pages/Character/ViewCharacter";
import { ViewItems } from "./pages/Items/ViewItems";
import { ViewSkill } from "./pages/Skill/ViewSkill";
import { TheCity } from "./pages/City/TheCity";
import { useLoadAppDataWithRetry } from "./services/axios/useLoadAppDataWithRetry";
import { AboutSettings } from "./pages/Settings/AboutSettings";
import { CharacterContextProvider } from "./pages/Character/CharacterContextProvider";
import { TheWilderness } from "./pages/Wilderness/TheWilderness";
import LanguageSwitcher from "./components/LanguageSwitcher";
import useCharacterService from "./services/character.service";
import { PrintCharacter } from "./pages/Character/print/PrintCharacter";
import PrintButtonWithModal from "./components/print/PrintButtonWithModal";

function App() {

  const { currentTheme } = useTheme();
  const { loading: loadingSkills, error: skillsLoadingError } = useLoadAppDataWithRetry("skills", "/skill");
  const [itemsEndpoint, setItemsEndpoint] = useState("/no-call");
  const [racesEndpoint, setRacesEndpoint] = useState("/no-call");
  const location = useLocation();

  const styles = {
    mainContainer: {
      width: "100%",
      maxWidth: 1200
    }
  };

  useEffect(() => {
    if (skillsLoadingError === null) {
      setItemsEndpoint("/items");
    }
  }, [loadingSkills, skillsLoadingError]);

  const { loading: loadingItems, error: itemsLoadingError } = useLoadAppDataWithRetry("items", itemsEndpoint);

  useEffect(() => {
    if (itemsLoadingError === null) {
      setRacesEndpoint("/race");
    }
  }, [loadingItems, itemsLoadingError]);

  const { loading: loadingRaces } = useLoadAppDataWithRetry("races", racesEndpoint);

  return (
    <CharacterContextProvider>
      {currentTheme && (
        <ThemeProvider theme={currentTheme}>
          <Backdrop
            sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer - 1 }}
            open={loadingSkills || loadingItems || loadingRaces}
          >
            <Box position="fixed" top={0} left={0} right={0}>
              <LinearProgress />
            </Box>
          </Backdrop>
          <GlobalStyles />
            <Box sx={{ p: 12 }}>
              <AppTabs />
              <Container style={styles.mainContainer} disableGutters>
                <Paper elevation={20}>
                  <Routes>
                    <Route index element={<Home />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/characters" element={<CharacterOverview />} />
                    <Route path="/city" element={<TheCity />} />
                    <Route path="/items" element={<ViewItems />} />
                    <Route path="/skill/:skillKey" element={<ViewSkill />} />
                    <Route path="/characters/:charId" element={<ViewCharacter />} />
                    <Route path="/wilderness" element={<TheWilderness />} />
                    <Route path="/settings" element={<AboutSettings />} />
                  </Routes>
                  <Box display="flex" justifyContent="flex-end"  >
                    {
                      // Multi-line expression using a function
                      (() => {
                        if(location.pathname.startsWith('/characters/')){
                          return <PrintButtonWithModal component={<PrintCharacter />} />;
                        }
                      })()
                    }
                    <LanguageSwitcher />
                  </Box>
                </Paper>
              </Container>
            </Box>
        </ThemeProvider>
      )}
    </CharacterContextProvider>
  );
}

export default App;
