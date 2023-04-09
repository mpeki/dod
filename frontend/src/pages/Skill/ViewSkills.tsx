import { useCallback, useEffect, useState } from "react";
import { SkillService } from "../../services/skill.service";
import { Skill } from "../../types/skill";
import { SkillList } from "./SkillList";
import { EnumSelect } from "../../components/EnumSelect";
import { Group } from "../../types/group";
import { BaseChance } from "../../types/basechance";

export const ViewSkills = () => {

  const [skills, setSkills] = useState<Skill[]>();
  const [shownSkills, setShownSkills] = useState<Skill[]>();


  const handleGroupSelect = (event: any) => {
    console.log(event.target.value)
    if(skills !== undefined){
      const selectedValue = event.target.value
      const shownSkills = skills.filter(skill => {
        return skill.group === selectedValue;
      });
      setShownSkills(selectedValue === 'ALL' ? skills : shownSkills)
    }
  };

  const handleBaseChanceSelect = (event: any) => {
    console.log(event.target.value)
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
      <EnumSelect selectType={Group} onChange={handleGroupSelect}/>
      <EnumSelect selectType={BaseChance} onChange={handleBaseChanceSelect}/>
      {/*<SkillList skills={shownSkills} selectSkillHandler={selectSkillHandler} />*/}
    </>)
}