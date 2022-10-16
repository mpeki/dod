import React from 'react';
import NewSkillForm from "./NewSkillForm";

const NewSkill = (props) => {
  const saveSkillDataHandler = (enteredSkillData) => {
    const skillData = {
      ...enteredSkillData,
      id: Math.random().toString()
    };
    props.onAddSkill(skillData);
  };
  return <div className="">
    <NewSkillForm onSaveSkillData={saveSkillDataHandler}/>
  </div>
};

export default NewSkill;
