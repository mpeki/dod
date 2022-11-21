import React from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import { Home } from "./pages/Home";
import { CharacterOverview } from "./pages/Character/CharacterOverview";
import { Layout } from "./pages/Layout";
import { ViewCharacter } from "./pages/Character/ViewCharacter";
import { ViewSkills } from "./pages/Skill/ViewSkills";
import { ViewSkill } from "./pages/Skill/ViewSkill";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/characters" element={<CharacterOverview />}  />
          <Route path="/skills" element={<ViewSkills/> }  />
          <Route path="/skill/:skillKey" element={<ViewSkill/> }  />
          <Route path="/characters/:charId" element={<ViewCharacter/>} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
