import { Skill } from "../../types/skill";
import { useState } from "react";
import { BuySkill } from "./BuySkill";
import { CharacterSkillList } from "./CharacterSkillList";
import { Character } from "../../types/character";
import { CharacterState } from "../../types/character-state";
import { IconButton, List, ListItem, ListItemText, Paper } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import KeyboardDoubleArrowDownIcon from "@mui/icons-material/KeyboardDoubleArrowDown";
import { StyledList } from "../../components/shared/List.styled";

interface IProps {
  character: Character;
  skills: Record<string, Skill> | undefined;
  fetchCharHandler: () => void;
}

export const SkillContainer = ({
                                 character,
                                 skills,
                                 fetchCharHandler
                               }: IProps): JSX.Element => {

  const [showBuySkill, setShowBuySkill] = useState<boolean>();

  const showBuySkillHandler = () => {
    if (showBuySkill) {
      setShowBuySkill(false);
    } else {
      setShowBuySkill(true);
    }
  };

  let canBuySkill = character.state === CharacterState.INIT_COMPLETE;
  let canTransferBonusXP: boolean = (character.baseSkillPoints !== undefined && character.baseSkillPoints > 0 && character.state === CharacterState.READY_TO_PLAY);


  return (
    <>
      <Paper elevation={3}>
        <List dense={true}>
          <StyledList>
            <ListItem dense={true}>
              <ListItemText primary={character.state === "READY_TO_PLAY" ? "Bonus Exp." : "Base Skill Points"} secondary={character.baseSkillPoints} />
              { canTransferBonusXP && (
              <IconButton>
                <KeyboardDoubleArrowDownIcon />
              </IconButton>
              )}
              { canBuySkill && (
                <IconButton onClick={showBuySkillHandler} disabled={!canBuySkill} edge="end" aria-label="add skill">
                  <AddIcon />
                </IconButton>
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
        <CharacterSkillList charId={character.id || ""} skills={skills || {}} />
      </Paper>
    </>
  );
};
