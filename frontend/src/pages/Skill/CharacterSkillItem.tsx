import { CharacterSkill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useCallback, useContext, useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { RemoveCircleOutline } from "@mui/icons-material";
import { useChangeService } from "../../services/change.service";
import { Change, createChange } from "../../types/change";
import { showSuccessSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";
import { useTranslation } from "react-i18next";
import CharacterContext from "../Character/CharacterContext";
import { ErrorMain } from "../Error/ErrorMain";

interface IProps {
  charSkill: CharacterSkill;
  canRemoveSkill: boolean;
  isPrinting: boolean;
}

export const CharacterSkillItem = ({ charSkill,
                                     canRemoveSkill,
                                     isPrinting
                                   }: IProps): JSX.Element => {

  const [showSkillDetails, setShowSkillDetails] = useState<boolean>();
  const { doCharacterChange } = useChangeService();
  const [changeData, setChangeData] = useState<Change>(createChange());
  const { t } = useTranslation(["skills", "items"]);
  const charContext = useContext(CharacterContext);

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { fetchCharHandler, currentCharacter } = charContext;

  const showSkillDetailsHandler = () => {
    if (showSkillDetails) {
      setShowSkillDetails(false);
    } else {
      setShowSkillDetails(true);
    }
  };

  const removeSkillHandler = useCallback(async () => {
    if (!currentCharacter) {
      showWarningSnackbar('Character is not defined');
      return;
    }
    const changePostData: Change = createChange("REMOVE_SKILL", "Remove skill", charSkill.skill.key, -1);
      await doCharacterChange(currentCharacter, changePostData)
      .then(() => {
        fetchCharHandler(currentCharacter.id).then((character) => showSuccessSnackbar("Removed skill! "));
      })
      .catch((e) => showWarningSnackbar((e as Error).message))
      .finally(() => {
        setChangeData(createChange());
        console.log("Change Data: ", changeData);
      });
  }, [currentCharacter, doCharacterChange, fetchCharHandler, charSkill.skill.key]);

  let skillCategory = JSON.stringify(charSkill.skill.category).replace(/"/g, "") as string;
  if(!currentCharacter){
    return <ErrorMain errorCode={-1} />;
  }
  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={currentCharacter.id} charSkill={charSkill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={charSkill.skill.key} style={{ height: "20px" }}>
        <TableCell onClick={showSkillDetailsHandler} sx={{
          fontSize: 13,
          borderRight: isPrinting ? 1 : 0,
          borderColor: isPrinting ? "darkgray" : "lightgray"
        }}>
          {charSkill.itemKey
            ? `${t(`items:${charSkill.itemKey}`)} (${t(charSkill.skill.key)})`
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
            {(canRemoveSkill && (charSkill.skill.key !== "dodge" && charSkill.skill.key !== "hand.to.hand")) && (
              <RemoveCircleOutline fontSize={"small"} color={"error"} onClick={removeSkillHandler} />
            )}
          </TableCell>
        )}
      </TableRow>
    </>
  );

};