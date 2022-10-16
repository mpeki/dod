import React from 'react';

import BaseTraitList from './BaseTraitList';
import Card from '../UI/Card';
import SkillList from './SkillList'

const CharacterSheet = (props) => {
  return (
      <Card className="character-sheet">
        <h2>DoD Character Sheet</h2>
        <BaseTraitList baseTraits={props.character.baseTraits} />
        <SkillList skills={props.skills} />
      </Card>
  );
}

export default CharacterSheet;
