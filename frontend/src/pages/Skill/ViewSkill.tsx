import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Skill } from "../../types/skill";

export const ViewSkill = () => {

  const { skillKey } = useParams();
  const [skill, setSkill] = useState<Skill>()

  useEffect(() => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON !== null) {
      const skills: Skill[] = JSON.parse(skillJSON);
      if(skills !== undefined){
        const skill: Skill = skills.filter(skill => {
          return skill.key === skillKey;
        })[0];
        setSkill(skill);
      }
    }
  }, [skillKey]);

  console.log(skill?.category)

  return (
    <>
      <div><h3>Skill name:</h3><p>{skill?.key}</p></div>
      <div><h3>Category: </h3><p>{JSON.stringify(skill?.category)}</p></div>
      <div><h3>Group: </h3> <p>{JSON.stringify(skill?.group)}</p></div>
      <div><h3>Trait Name:</h3><p>{skill?.traitName}</p></div>
      <div><h3>Base Chance:</h3><p>{skill?.baseChance}</p></div>
      <div><h3>Price:</h3><p>{skill?.price}</p></div>
    </>
  );
};