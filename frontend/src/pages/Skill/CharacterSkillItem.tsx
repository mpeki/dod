import { CharacterSkill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useCallback, useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { RemoveCircleOutline } from "@mui/icons-material";
import { useChangeService } from "../../services/change.service";
import { Change, createChange } from "../../types/change";
import { showWarningSnackbar } from "../../utils/DODSnackbars";
import { useTranslation } from "react-i18next";
import { Character } from "../../types/character";

interface IProps {
  characterId: string;
  charSkill: CharacterSkill;
  fetchCharHandler: (charId: string) => Promise<Character>;
  canRemoveSkill: boolean;
  isPrinting: boolean;
}

export const CharacterSkillItem = ({
                                     characterId,
                                     charSkill,
                                     fetchCharHandler,
                                     canRemoveSkill,
                                     isPrinting
                                   }: IProps): JSX.Element => {

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
    .then(() => fetchCharHandler(characterId))
    .catch((e) => showWarningSnackbar((e as Error).message))
    .finally(() => {
      setChangeData(createChange());
    });
  }, [characterId, doChange, fetchCharHandler, charSkill.skill.key]);

  let skillCategory = JSON.stringify(charSkill.skill.category).replace(/"/g, "") as string;
  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={characterId} charSkill={charSkill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={charSkill.skill.key} style={{ height: "20px" }}>
        <TableCell onClick={showSkillDetailsHandler} sx={{
          fontSize: 13,
          borderRight: isPrinting ? 1 : 0,
          borderColor: isPrinting ? "darkgray" : "lightgray"
        }}>
          {charSkill.skill.itemKey
            ? `${t(charSkill.skill.itemKey)} (${t(charSkill.skill.key)})`
            : t(charSkill.skill.key)
          }
        </TableCell>
        <TableCell sx={{
          fontSize: 13,
          borderRight: isPrinting ? 1 : 0,
          borderColor: isPrinting ? "darkgray" : "lightgray"
        }}>{skillCategory === "B" ? "B" + charSkill.fv : charSkill.fv}</TableCell>
        <TableCell sx={{
          fontSize: 13,
          borderColor: isPrinting ? "darkgray" : "lightgray"
        }}>{isPrinting ? "" : charSkill.experience}</TableCell>
        {!isPrinting && (
          <TableCell>
            {(canRemoveSkill && (charSkill.skill.key !== "dodge")) && (
              <RemoveCircleOutline fontSize={"small"} color={"error"} onClick={removeSkillHandler} />
            )}
          </TableCell>
        )}
      </TableRow>
    </>
  );

};