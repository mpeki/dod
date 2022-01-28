import React from 'react';

import BaseTraitList from './components/CharacterSheet/BaseTraitList';

const App = () => {
  const character = {
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
    "baseSkillPoints": 260
  };

  // return React.createElement(
  //   'div',
  //   {},
  //   React.createElement('h2', {}, "Let's get started!"),
  //   React.createElement(Expenses, { items: expenses })
  // );

  return (
    <div>
      <h2>DOD Character Sheet</h2>
      <BaseTraitList baseTraits={character.baseTraits} />
    </div>
  );
}

export default App;
