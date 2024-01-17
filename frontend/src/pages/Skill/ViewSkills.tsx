import { useState } from "react";
import { Skill } from "../../types/skill";
import { EnumSelect } from "../../components/EnumSelect";
import { GroupType } from "../../types/group";
import { BaseChance } from "../../types/basechance";

export const ViewSkills = () => {

  const [skills, setSkills] = useState<Skill[]>();
  const [shownSkills, setShownSkills] = useState<Skill[]>();


  const handleGroupSelect = (event: any) => {
    if(skills !== undefined){
      const selectedValue = event.target.value
      const shownSkills = skills.filter(skill => {
        return skill.group === selectedValue;
      });
      setShownSkills(selectedValue === 'ALL' ? skills : shownSkills)
    }
  };

  const handleBaseChanceSelect = (event: any) => {
    if(skills !== undefined){
      const selectedValue = event.target.value
      const shownSkills = skills.filter(skill => {
        return skill.baseChance === selectedValue;
      });
      setShownSkills( selectedValue === 'ALL' ? shownSkills : shownSkills)
    }
  };

  return (
    <>
      <EnumSelect selectType={GroupType} onChange={handleGroupSelect}/>
      <EnumSelect selectType={BaseChance} onChange={handleBaseChanceSelect}/>
      {/*<SkillList skills={shownSkills} selectSkillHandler={selectSkillHandler} />*/}
    </>)
}