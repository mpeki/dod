import { Category } from "../types/category";
import { GroupType } from "../types/group";
import { useCallback, useEffect, useState } from "react";
import { useSkillService } from "../services/skill.service";
import { Skill } from "../types/skill";
import Select from "react-select";
// import Select, { SelectChangeEvent } from '@mui/material/Select';
import { EnumHelpers } from "../utils/EnumHelpers";
import makeAnimated from "react-select/animated";
import { SkillList } from "../pages/Skill/SkillList";
import { OnChangeValue } from "react-select/dist/declarations/src/types";
import { showFatalConnectionErrorSnackbar } from "../utils/DODSnackbars";
import { Grid } from "@mui/material";
import { useTranslation } from "react-i18next";


interface IProps {
  selectSkillHandler: (skill: Skill) => void;
  excludedSkills?: Record<string,Skill>;
  raceName: string;
}

export interface GroupedOption {
  readonly label: string;
  readonly compValue: string;
  readonly options: any;
}

const animatedComponents = makeAnimated();

export const SkillSelector = ({ selectSkillHandler, excludedSkills, raceName }: IProps): JSX.Element => {

  const { getAllSkills } = useSkillService();
  const [shownSkills, setShownSkills] = useState<Skill[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);
  const [hasFiltersApplied, setHasFiltersApplied] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState<any[]>([]);
  const { t } = useTranslation("skills");

  const rawGroupedFilterOptions: readonly GroupedOption[] = [
    {
      label: t("categoryTitle"),
      compValue: "category",
      options: EnumHelpers.getLabelAndValues(new Category())

    },
    {
      label: t("groupTitle"),
      compValue: "group",
      options: EnumHelpers.getLabelAndValues(new GroupType())
    }
  ];

  const groupedFilterOptions = rawGroupedFilterOptions.map(group => ({
    ...group,
    options: group.options.map((option: any) => ({
      ...option,
      label: t(option.value)
    }))
  }));


  const filterListHandler = (selected: OnChangeValue<any, any>) => {
    setSelectedOptions(selected);
    if (skills !== undefined) {
      if (selected.length === 0) {
        setShownSkills(skills);
        setHasFiltersApplied(false);
      } else {
        setHasFiltersApplied(true);
        let result: Skill[] = shownSkills.length === 0 || selected.length === 1 ? skills : shownSkills;
        for (const selectedElement of selected) {
          result = result.filter(skill => {
            return selectedElement.type === "category" ? skill.category === selectedElement.value.toUpperCase() : skill.group === selectedElement.value.toUpperCase();
          });
          setShownSkills(result);
        }
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
    setShownSkills(skills);
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

  const NoFiltersMessageComponent = () => <div>{t("buySkillsForm.noFiltersMessage")}</div>;

  const allExcludedSkills : Record<string,Skill> = { ...excludedSkills, ...racialExcludedSkills };
  return (
    <Grid container spacing={1} direction={"row"}>
      <Grid item xs={6} md={6}>
        <Select
          autoFocus={true}
          isMulti
          components={animatedComponents}
          options={groupedFilterOptions.filter(option => {
            // if no filter is selected, show all options
            if (selectedOptions.length === 0) {
              return true;
            }
            // only show options of the type(s) that are currently not selected
            return !selectedOptions.some(selectedOption => selectedOption.type === option.compValue);
          })}
          onChange={filterListHandler}
          placeholder={t("buySkillsForm.filterPlaceholder")}
          noOptionsMessage={() => <NoFiltersMessageComponent/>}
        />
      </Grid>
      <Grid item xs={6} md={6}>
          <SkillList excludedSkills={allExcludedSkills} skills={shownSkills.length === 0 && !hasFiltersApplied? skills : shownSkills} selectSkillHandler={selectSkillHandler}/>
      </Grid>
    </Grid>
  );
};