import React, { useState } from 'react';

import CharacterSheet from "./components/CharacterSheet/CharacterSheet";
import NewCharacter from "./components/NewCharacter/NewCharacter";
import NewSkill from "./components/NewSkill/NewSkill";

const mock_character = {
  "id": 2,
  "name": "Peter",
  "baseTraits": {
    "PSYCHE": {
      "id": 1,
      "traitName": "PSYCHE",
      "value": 10,
      "startValue": 10,
      "groupValue": 2
    },
    "SIZE": {
      "id": 2,
      "traitName": "SIZE",
      "value": 10,
      "startValue": 10,
      "groupValue": 2
    },
    "CONSTITUTION": {
      "id": 3,
      "traitName": "CONSTITUTION",
      "value": 13,
      "startValue": 13,
      "groupValue": 3
    },
    "DEXTERITY": {
      "id": 4,
      "traitName": "DEXTERITY",
      "value": 13,
      "startValue": 12,
      "groupValue": 2
    },
    "INTELLIGENCE": {
      "id": 5,
      "traitName": "INTELLIGENCE",
      "value": 12,
      "startValue": 12,
      "groupValue": 2
    },
    "STRENGTH": {
      "id": 6,
      "traitName": "STRENGTH",
      "value": 10,
      "startValue": 10,
      "groupValue": 2
    },
    "CHARISMA": {
      "id": 7,
      "traitName": "CHARISMA",
      "value": 7,
      "startValue": 7,
      "groupValue": 1
    }
  },
  "race": {
    "id": 1,
    "name": "human"
  },
  "ageGroup": "YOUNG",
  "state": "AGE_GROUP_SET",
  "baseSkillPoints": 260,
  "currentSkillPoints": 0,
  "skills": [
    { "id" : 23452, "name" : "Finde Ting", "fv": 12, "sp": 0 },
    { "id" : 23430, "name" : "Opdage Fare", "fv": 14, "sp": 0 },
    { "id" : 23478, "name" : "Ride", "fv": 10, "sp": 0 },
    { "id" : 23433, "name" : "Undvige", "fv": 15, "sp": 0 },
    { "id" : 23413, "name" : "Kortsværd", "fv": 14, "sp": 0 }
  ]
};

const mock_skills = mock_character.skills

const App = () => {
  const  [skills, setSkills] = useState(mock_skills)
  const addSkillHandler = (skill) => {
    console.log(skills.name)
    setSkills((prevSkills) => {
      return [skill, ...prevSkills];
    });
  }
  return (
    <div>
      <NewCharacter/>
      <NewSkill onAddSkill={addSkillHandler} />
      <CharacterSheet title="DoD Character Sheet" character={mock_character} skills={skills}/>
    </div>
  );
}

export default App;
