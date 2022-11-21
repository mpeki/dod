import { Skill } from "../../types/skill";
import Select from "react-select";
import { useState } from "react";


interface IProps {
  skills: Skill[] | undefined;
  selectSkillHandler: (skill: Skill) => void;
}

export const SkillList = ({ skills, selectSkillHandler }: IProps): JSX.Element => {

  const skillItems = () => {
    let options: any[] = [];
    if(skills){
      options = skills.map((skill) => {
        return {
          value: skill.key,
          label: skill.key,
        };
      });
    }
    return options;
  };

  //get the value of the selected skill and set it to the state, lift the state to the parent component
  const handleChange = (selectedOption: any) => {
    // setSelectedSkill(selectedOption);
    console.log(`Option selected:`, selectedOption);
    const selectedSkill : Skill | undefined = skills?.find((skill) => skill.key === selectedOption.value);
    console.log("Selected Skill: " +JSON.stringify(selectedSkill));
    if(selectedSkill){
      selectSkillHandler(selectedSkill);
    }
  };

  return (
      <Select
        options={skillItems()}
        onChange={handleChange}
      />
  );
};