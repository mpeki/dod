import React from 'react';
import SkillItem from "./SkillItem";

const SkillList = (props) => {
  return (
      <div>
        <h1>Færdigheder</h1>
        { props.skills.map(skill => <SkillItem key={skill.id} name={skill.name} fv={skill.fv} /> ) }
      </div>
  )
}

export default SkillList
