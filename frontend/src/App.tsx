import React, { useCallback, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import { ThemeProvider } from "styled-components";
import { useTheme } from "./Theme/useTheme";
import { GlobalStyles } from "./App.styled";
import { Box, Backdrop, LinearProgress } from "@mui/material";
import { AppRouter } from "./components/routing/AppRouter";
import { CurrentRoute } from "./components/routing/CurrentRoute";
import { AppTabs } from "./components/routing/AppTabs";
import { Home } from "./pages/Home";
import { CharacterOverview } from "./pages/Character/CharacterOverview";
import { ViewCharacter } from "./pages/Character/ViewCharacter";
import { ViewItems } from "./pages/Items/ViewItems";
import { ViewSkill } from "./pages/Skill/ViewSkill";
import { SkillService } from "./services/skill.service";
import { ItemService } from "./services/item.service";

function App() {
  const { currentTheme, setActiveTheme } = useTheme();

  const [open, setOpen] = React.useState(false);
  const handleClose = () => {
    setOpen(false);
  };
  const handleOpen = () => {
    setOpen(true);
  };

  const fetchSkillsHandler = useCallback(async () => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON === null) {
      await SkillService.getAllSkills()
      .then((skills) => {
        skillJSON = JSON.stringify(skills);
        localStorage.setItem("skills", skillJSON);
        console.log(skills)
      })
      .catch((e) => alert("Error fetching skills: " + e));
    }
  }, []);

  const fetchItemsHandler = useCallback(async () => {
    let itemJSON = localStorage.getItem("items");
    if (itemJSON === null) {
      await ItemService.getAllItems()
      .then((items) => {
        itemJSON = JSON.stringify(items);
        localStorage.setItem("items", itemJSON);
        console.log(items)
      })
      .catch((e) => alert("Error fetching items: " + e));
    }
  }, []);

  useEffect(() => {
    handleOpen();
    console.log("useEffect fetchSkills...")
    fetchSkillsHandler().then();
    fetchItemsHandler().then(handleClose)
  }, [fetchSkillsHandler, fetchItemsHandler]);

  return (
    <>
      {currentTheme && (
        <ThemeProvider theme={currentTheme}>
          <GlobalStyles />
          <AppRouter>
            <Box sx={{ width: "100%" }}>
              <AppTabs />
              <Routes>
                <Route index element={<Home />} />
                <Route path="/home" element={<Home />} />
                <Route path="/characters" element={<CharacterOverview />} />
                <Route path="/items" element={<ViewItems />} />
                <Route path="/skill/:skillKey" element={<ViewSkill />} />
                <Route path="/characters/:charId" element={<ViewCharacter />} />
              </Routes>
              <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={open}
              >
                <Box sx={{ width: '100%' }}>
                  <LinearProgress />
                </Box>
              </Backdrop>

            </Box>
          </AppRouter>
        </ThemeProvider>
      )}
    </>
  );
}

export default App;
