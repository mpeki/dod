import { Skill } from "../../types/skill";
import Select from 'react-select';
import { useState } from "react";
import { useTranslation } from "react-i18next";


interface IProps {
  skills: Skill[] | undefined;
  excludedSkills?: Record<string,Skill>;
  selectSkillHandler: (skill: Skill) => void;
}

export const SkillList = ({ skills, excludedSkills, selectSkillHandler }: IProps): JSX.Element => {

  const [selectedValue, setSelectedValue] = useState(null)
  const { t } = useTranslation("skills");

  const skillItems = () => {
    let options: any[] = [];
    if(skills){
      skills = skills.filter(skill => {
        if(excludedSkills){
          return !excludedSkills[skill.key];
        }
        return true;
      });
      options = skills.map((skill) => {
        return {
          value: skill.key,
          label: t(skill.key),
        };
      });
    }
    return options;
  };

  //get the value of the selected skill and set it to the state, lift the state to the parent component
  const handleChange = (selectedOption: any) => {
    setSelectedValue(selectedOption);
    if (selectedOption) {
      const selectedSkill : Skill | undefined = skills?.find((skill) => skill.key === selectedOption.value);
      if(selectedSkill){
        selectSkillHandler(selectedSkill);
      }
    }
  };


  const NoOptionsMessageComponent = () => <div>{t("buySkillsForm.noSkillsMessage")}</div>;

  return (
      <Select
        options={skillItems()}
        onChange={handleChange}
        value={selectedValue}
        placeholder={t("buySkillsForm.skillPlaceholder")}
        noOptionsMessage={() => <NoOptionsMessageComponent/>}
        isClearable
      />
  );
};