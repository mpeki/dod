import { Skill } from "../../types/skill";
import { useContext, useState } from "react";
import { BuySkill } from "./BuySkill";
import { CharacterSkillList } from "./CharacterSkillList";
import { Character } from "../../types/character";
import { CharacterState } from "../../types/character-state";
import { Fab, IconButton, List, ListItem, ListItemText, Paper } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import KeyboardDoubleArrowDownIcon from "@mui/icons-material/KeyboardDoubleArrowDown";
import { StyledList } from "../../components/shared/List.styled";
import withFlashing from "../../components/withFlashing";
import StartIcon from "@mui/icons-material/Start";
import CharacterContext from "../Character/CharacterContext";
import { KeyboardShortcutProvider } from "../../components/KeyboardShortcutProvider";
import useKeyboardShortcut from "../../components/KeyboardShortcutContext";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";


interface IProps {
  character: Character;
  skills: Record<string, Skill> | undefined;
  fetchCharHandler: () => void;
}

export const SkillContainer = ({ character, skills, fetchCharHandler }: IProps): JSX.Element => {

  const [showBuySkill, setShowBuySkill] = useState<boolean>();
  const FlashingAddButton = withFlashing(Fab);
  const FlashingActivateButton = withFlashing(Fab);
  const charContext = useContext(CharacterContext);
  const { t } = useTranslation("char");

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { activateCharHandler } = charContext;

  const showBuySkillHandler = () => {
    if (showBuySkill) {
      setShowBuySkill(false);
    } else {
      setShowBuySkill(true);
    }
  };

  const canActivate: boolean = (character.baseSkillPoints !== undefined && character.baseSkillPoints < 10 && character.state === CharacterState.INIT_COMPLETE);
  let canBuySkill = character.state === CharacterState.INIT_COMPLETE && character.baseSkillPoints !== undefined && character.baseSkillPoints > 0;
  let canTransferBonusXP: boolean = (character.baseSkillPoints !== undefined && character.baseSkillPoints > 0 && character.state === CharacterState.READY_TO_PLAY);
  const shortcuts = canBuySkill ? [{ key: "+", callback: () => showBuySkillHandler() }] : [];

  const handleActivation = () => {
    const characterId = character.id ? character.id : "";
    activateCharHandler(characterId).then(() => fetchCharHandler());
  };

  const navigate = useNavigate();
  useKeyboardShortcut(['Escape'], (event) => {
    const targetElement = event.target as HTMLElement;
    if (
      targetElement.tagName === "INPUT" ||
      targetElement.tagName === "TEXTAREA" ||
      targetElement.tagName === "SELECT"
    ) {
      event.key === "Escape" && targetElement.blur();
      return; // If it is, do not proceed with executing the keyboard shortcut
    } else {
      showBuySkill ? showBuySkillHandler() : navigate("/characters");
    }
  });

  let canRemoveSkill = character.state === CharacterState.INIT_COMPLETE;

  return (
    <>
      <KeyboardShortcutProvider shortcuts={shortcuts}>
        <Paper elevation={3}>
          <List dense={true}>
            <StyledList>
              <ListItem dense={true}>
                <ListItemText primary={character.state === "READY_TO_PLAY" ? t("detail.skills.bonusExp") : t("detail.skills.basisSP")}
                              secondary={character.baseSkillPoints} />
                {canActivate && (
                  <FlashingActivateButton onClick={handleActivation} aria-label="activate" sx={{ mr: 1 }} size={"small"}
                                          color={"success"}>
                    <StartIcon />
                  </FlashingActivateButton>
                )}
                {canTransferBonusXP && (
                  <IconButton>
                    <KeyboardDoubleArrowDownIcon />
                  </IconButton>
                )}
                {(!showBuySkill && canBuySkill) && (
                  <FlashingAddButton onClick={showBuySkillHandler} disabled={!canBuySkill} size="small"
                                     color={"success"}
                                     aria-label="add skill"
                                     skipflash={skills === undefined ? false : Object.keys(skills).length > 0}>
                    <AddIcon />
                  </FlashingAddButton>
                )}
              </ListItem>
            </StyledList>
          </List>
        </Paper>
        <Paper elevation={3}>
          {showBuySkill && (
            <BuySkill
              character={character}
              onConfirm={showBuySkillHandler}
              buySkillHandler={fetchCharHandler}
            />
          )}
          <CharacterSkillList charId={character.id || ""} skills={skills || {}} fetchCharHandler={fetchCharHandler} canRemoveSkill={canRemoveSkill} />
        </Paper>
      </KeyboardShortcutProvider>
    </>
  );
};
