import { Category } from "../types/category";
import { GroupType } from "../types/group";
import { useCallback, useEffect, useState } from "react";
import { useSkillService } from "../services/skill.service";
import { Skill } from "../types/skill";
import Select from "react-select";
import { EnumHelpers } from "../utils/EnumHelpers";
import makeAnimated from "react-select/animated";
import { SkillList } from "../pages/Skill/SkillList";
import { OnChangeValue } from "react-select/dist/declarations/src/types";
import { showFatalConnectionErrorSnackbar } from "../utils/DODSnackbars";


interface IProps {
  selectSkillHandler: (skill: Skill) => void;
  excludedSkills?: Record<string,Skill>;
  raceName: string;
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
    options: EnumHelpers.getLabelAndValues(new GroupType())
  }
];

const animatedComponents = makeAnimated();

export const SkillSelector = ({ selectSkillHandler, excludedSkills, raceName }: IProps): JSX.Element => {

  const { getAllSkills } = useSkillService();
  const [shownSkills, setShownSkills] = useState<Skill[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);

  const filterListHandler = (selected: OnChangeValue<any, any>) => {
    if (skills !== undefined) {
      if (selected.length === 0) {
        setShownSkills([]);
      } else {
        let result: Skill[] = shownSkills.length === 0 || selected.length === 1 ? skills : shownSkills;
        for (const selectedElement of selected) {
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
        await getAllSkills().then((skills) => {
          skillJSON = JSON.stringify(skills);
          localStorage.setItem("skills", skillJSON);
        }).catch((e) => {showFatalConnectionErrorSnackbar("Could not load skills.", false);});
    }
    setSkills(skillJSON === null ? null : JSON.parse(skillJSON));
  }, [getAllSkills]);

  useEffect(() => {
    fetchSkillsHandler().then();
  }, [fetchSkillsHandler]);

  const racialExcludedSkills = skills.filter(skill =>
    skill.deniedRaces && skill.deniedRaces.length > 0 && skill.deniedRaces.some(race => race.name === raceName)
  ).reduce((acc, skill) => {
    acc[skill.key] = skill;
    return acc;
  }, {} as Record<string, Skill>);

  const allExcludedSkills : Record<string,Skill> = { ...excludedSkills, ...racialExcludedSkills };
  return (
    <>
      <div>Filter skills:</div>
      <Select
        autoFocus={true}
        isMulti
        components={animatedComponents}
        options={groupedFilterOptions}
        onChange={filterListHandler}
      />
      <div>Select a skill:</div>
      <SkillList excludedSkills={allExcludedSkills} skills={shownSkills} selectSkillHandler={selectSkillHandler}/>
    </>
  );
};