import { CharacterSkill, Skill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useCallback, useEffect, useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { RemoveCircleOutline } from "@mui/icons-material";
import { useChangeService } from "../../services/change.service";
import { Change, createChange } from "../../types/change";
import { showWarningSnackbar } from "../../utils/DODSnackbars";
import { useTranslation } from "react-i18next";
import { GroupType } from "../../types/group";

interface IProps {
  characterId: string;
  charSkill: CharacterSkill;
  fetchCharHandler: () => void;
  canRemoveSkill: boolean;
}

export const CharacterSkillItem = ({ characterId, charSkill, fetchCharHandler, canRemoveSkill}: IProps): JSX.Element => {

  const [showSkillDetails, setShowSkillDetails] = useState<boolean>();
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
    const changePostData: Change = createChange("REMOVE_SKILL", "Remove skill", charSkill.skill.key, -1);
    await doChange(characterId, changePostData)
    .then(() => fetchCharHandler())
    .catch((e) => showWarningSnackbar((e as Error).message))
    .finally(() => {
      setChangeData(createChange())
    });
  }, [characterId, doChange, fetchCharHandler, charSkill.skill.key]);

  let skillCategory = JSON.stringify(charSkill.skill.category).replace(/"/g, "") as string;
  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={characterId} charSkill={charSkill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={charSkill.skill.key}>
        <TableCell>
        { (canRemoveSkill && (charSkill.skill.key !== "dodge")) && (
            <RemoveCircleOutline fontSize={"small"} color={"error"} onClick={removeSkillHandler} />
        )}
        </TableCell>
        <TableCell onClick={showSkillDetailsHandler}>
          {charSkill.skill.itemKey
            ? `${t(charSkill.skill.itemKey)} (${t(charSkill.skill.key)})`
            : t(charSkill.skill.key)
          }
        </TableCell>
        <TableCell>{skillCategory === "B" ? "B" + charSkill.fv : charSkill.fv}</TableCell>
        <TableCell>{charSkill.experience}</TableCell>
      </TableRow>
    </>
  );

};