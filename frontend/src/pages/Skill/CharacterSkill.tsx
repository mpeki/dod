import { Skill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useCallback, useEffect, useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { RemoveCircleOutline } from "@mui/icons-material";
import { useChangeService } from "../../services/change.service";
import { Change, createChange } from "../../types/change";
import { showWarningSnackbar } from "../../utils/DODSnackbars";
import { useTranslation } from "react-i18next";

interface IProps {
  characterId: string;
  skill: Skill;
  fetchCharHandler: () => void;
  canRemoveSkill: boolean;
}

export const CharacterSkill = ({ characterId, skill, fetchCharHandler, canRemoveSkill}: IProps): JSX.Element => {

  const [showSkillDetails, setShowSkillDetails] = useState<boolean>();
  const [memSkill, setMemSkill] = useState<Skill>();
  const { doChange } = useChangeService();
  const [changeData, setChangeData] = useState<Change>(createChange());
  const { t } = useTranslation("skills");

  const showSkillDetailsHandler = () => {
    if (showSkillDetails) {
      setShowSkillDetails(false);
    } else {
      setShowSkillDetails(true);
    }
  };

  const removeSkillHandler = useCallback(async () => {
    const changePostData: Change = createChange("REMOVE_SKILL", "Remove skill", skill.key, -1);
    await doChange(characterId, changePostData)
    .then(() => fetchCharHandler())
    .catch((e) => showWarningSnackbar((e as Error).message))
    .finally(() => {
      setChangeData(createChange())
    });
  }, [changeData, characterId, doChange, fetchCharHandler, skill.key]);

  useEffect(() => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON !== null) {
      const skills: Skill[] = JSON.parse(skillJSON);
      if (skills !== undefined) {
        const memSkill: Skill = skills.filter(storedSkill => {
          return storedSkill.key === skill.key;
        })[0];
        memSkill.fv = skill.fv;
        memSkill.experience = skill.experience;
        memSkill.itemKey = skill.itemKey;
        setMemSkill(memSkill);
      }
    }
  }, [skill]);

  if (memSkill === undefined) {
    return <TableRow><TableCell>skill!</TableCell></TableRow>;
  }
  let skillCategory = JSON.stringify(memSkill.category).replace(/"/g, "") as string;

  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={characterId} skill={memSkill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={memSkill.key}>
        {canRemoveSkill && (
          <TableCell>
            <RemoveCircleOutline fontSize={"small"} color={"error"} onClick={removeSkillHandler} />
          </TableCell>
        )}
        <TableCell onClick={showSkillDetailsHandler}>
          {memSkill.itemKey
            ? `${t(memSkill.itemKey)} (${t(memSkill.key)})`
            : t(memSkill.key)
          }
        </TableCell>
        <TableCell>{skillCategory === "B" ? "B" + memSkill.fv : memSkill.fv}</TableCell>
        <TableCell>{memSkill.experience}</TableCell>
      </TableRow>
    </>
  );

};