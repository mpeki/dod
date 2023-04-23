import { Category } from "../types/category";
import { Group } from "../types/group";
import { useCallback, useEffect, useState } from "react";
import { SkillService } from "../services/skill.service";
import { Skill } from "../types/skill";
import Select from "react-select";
import { EnumHelpers } from "../utils/EnumHelpers";
import makeAnimated from "react-select/animated";
import { SkillList } from "../pages/Skill/SkillList";
import { OnChangeValue } from "react-select/dist/declarations/src/types";


interface IProps {
  selectSkillHandler: (skill: Skill) => void;
}

export interface GroupedOption {
  readonly label: string;
  readonly options: any;
}

export const groupedFilterOptions: readonly GroupedOption[] = [
  {
    label: "Category",
    options: EnumHelpers.getLabelAndValues(new Category())

  },
  {
    label: "Group",
    options: EnumHelpers.getLabelAndValues(new Group())
  }
];

const animatedComponents = makeAnimated();

export const SkillSelector = ({ selectSkillHandler }: IProps): JSX.Element => {

  const [shownSkills, setShownSkills] = useState<Skill[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);

  const filterListHandler = (selected: OnChangeValue<any, any>) => {
    console.log("Filter: " + JSON.stringify(selected));

    if (skills !== undefined) {
      if (selected.length === 0) {
        setShownSkills([]);
      } else {
        let result: Skill[] = shownSkills.length === 0 || selected.length === 1 ? skills : shownSkills;
        for (const selectedElement of selected) {
          console.log("selectedElement: " + JSON.stringify(selectedElement));
          result = result.filter(skill => {
            return selectedElement.type === "Category" ? skill.category === selectedElement.label.toUpperCase() : skill.group === selectedElement.label.toUpperCase();
          });
        }
        setShownSkills(result);
      }
    }
  };

  const fetchSkillsHandler = useCallback(async () => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON === null) {
      await SkillService.getAllSkills()
      .then((skills) => {
        skillJSON = JSON.stringify(skills);
        localStorage.setItem("skills", skillJSON);
        setSkills(skills);
      })
      .catch((e) => alert("Error fetching skills: " + e));
    }
    setSkills(skillJSON === null ? null : JSON.parse(skillJSON));
  }, []);

  useEffect(() => {
    fetchSkillsHandler().then();
  }, [fetchSkillsHandler]);

  return (
    <>
      <div>Filter skills:</div>
      <Select
        isMulti
        components={animatedComponents}
        options={groupedFilterOptions}
        onChange={filterListHandler}
      />
      <div>Select a skill:</div>
      <SkillList skills={shownSkills} selectSkillHandler={selectSkillHandler}/>
    </>
  );
};