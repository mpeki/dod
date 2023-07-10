import { Skill } from "../../types/skill";
import Select from "react-select";


interface IProps {
  skills: Skill[] | undefined;
  charSkills?: Record<string,Skill>;
  selectSkillHandler: (skill: Skill) => void;
}

export const SkillList = ({ skills, charSkills, selectSkillHandler }: IProps): JSX.Element => {

  const skillItems = () => {
    let options: any[] = [];
    if(skills){
      skills = skills.filter(skill => {
        if(charSkills){
          return !charSkills[skill.key];
        }
        return true;
      });
      console.log("Filtered skills: " + JSON.stringify(skills));
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