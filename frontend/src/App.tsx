import React, { useCallback, useEffect } from "react";
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
import { SkillService } from "./services/skill.service";
import { ItemService } from "./services/item.service";
import { showFatalConnectionErrorSnackbar } from "./utils/DODSnackbars";
import { closeSnackbar } from "notistack";

function App() {
  const { currentTheme, setActiveTheme } = useTheme();
  const [loading, setLoading] = React.useState(false);

  const fetchSkillsHandler = useCallback(async () => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON === null) {
      try {
        setLoading(true);
        const skills = await SkillService.getAllSkills();
        skillJSON = JSON.stringify(skills);
        localStorage.setItem("skills", skillJSON);
        setLoading(false);
        return true;
      } catch (e) {
        console.log("error: " + e);
        showFatalConnectionErrorSnackbar();
        setLoading(false);
        return false;
      }
    } else {
      console.log("skills already cached.")
      closeSnackbar();
      return true;
    }
  }, []);

  const fetchItemsHandler = useCallback(async () => {
    let itemJSON = localStorage.getItem("items");
    if (itemJSON === null) {
      try {
        setLoading(true);
        const items = await ItemService.getAllItems();
        itemJSON = JSON.stringify(items);
        localStorage.setItem("items", itemJSON);
        setLoading(false);
      } catch (e) {
        console.log("error: " + e);
        showFatalConnectionErrorSnackbar();
        setLoading(false);
      }
    } else {
      console.log("skills already cached.")
      closeSnackbar();
      return true;
    }
  }, []);

  useEffect(() => {
    console.log("caching skills ...");
    fetchSkillsHandler().then(success => {
      if (success) {
        console.log("caching items ...");
        fetchItemsHandler().then();
      }
    });
  }, [fetchSkillsHandler, fetchItemsHandler]);


  return (
    <>
      {currentTheme && (
        <ThemeProvider theme={currentTheme}>
          <Backdrop
            sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer -1 }}
            open={loading}
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
