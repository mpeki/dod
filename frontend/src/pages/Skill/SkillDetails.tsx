import { Skill } from "../../types/skill";
import { Action } from "../../types/action";
import classes from "./SkillDetails.module.css";
import React, { useCallback, useState } from "react";
import { useSkillService } from "../../services/skill.service";
import { Change, createChange } from "../../types/change";
import { useChangeService } from "../../services/change.service";
import { Snackbar } from "@mui/material";
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import { showWarningSnackbar } from "../../utils/DODSnackbars";

interface IProps {
  characterId: string;
  skill: Skill;
  onConfirm: any;
}

const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
  props,
  ref,
) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export const SkillDetails = ({ characterId, skill, onConfirm }: IProps): JSX.Element => {

  const { doChange } = useChangeService();
  const { trainSkill } = useSkillService();
  const [action , setAction] = useState<Action>();
  const [skillInput, setSkillInput] = useState<Skill>(skill);
  const [open, setOpen] = useState(false);

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  const trainSkillHandler = useCallback(async () => {
    await trainSkill(characterId, skill.key)
    .then((action) => {
      if (action.actor.skills !== undefined) {
        setAction(action);
        skillInput.experience = action.actor.skills[skill.key].experience;
        skillInput.fv = action.actor.skills[skill.key].fv;
        setSkillInput(skillInput);
        handleClick();
      }

    })
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, [trainSkill, characterId, skill.key, skillInput]);


  const exchangeXPHandler = useCallback(async () => {
    const changeData: Change = createChange("SKILL_CHANGE", "Increase FV for skill", skill.key, 1);

    if(characterId != null) {
      await doChange(characterId, changeData)
      .then((change: Change) => {
        if (change.objectAfterChange?.skills !== undefined) {
          console.log(change.objectAfterChange?.skills[skill.key]);
          skillInput.experience = change.objectAfterChange.skills[skill.key].experience;
          skillInput.fv = change.objectAfterChange.skills[skill.key].fv;
          setSkillInput(skillInput);
          handleClick();
        }
      });
    }
  }, [characterId, doChange, skill.key, skillInput]);

  return (
    <>
      <div>
        <div className={classes.backdrop} onClick={onConfirm}></div>
        <div className={classes.modal}>
          <header className={classes.header}>
            <h2>Skill Details</h2>
          </header>
          <div className={classes.content}>
            <p>Name: {skillInput.key}</p>
            <p>FV: {skillInput.fv}</p>
            <p>Skill Points: {skillInput.experience}</p>
            <p>Base Chance: {skillInput?.baseChance} group</p>
            <p>Category: {skillInput.category ? skillInput.category.toString() : ''}</p>
            <p>Group: {skillInput.group ? skillInput.group.toString() : ''}</p>
            <p>Trait: {skillInput?.traitName}</p>
            <p>Price: {skillInput?.price}</p>
          </div>

          <footer className={classes.actions}>
            <button onClick={exchangeXPHandler}>Buy FV</button>
            <button onClick={trainSkillHandler}>Train Skill</button>
            <button onClick={onConfirm}>Ok</button>
          </footer>
          <Snackbar open={open} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}>
            <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
              {action?.actionResult}
            </Alert>
          </Snackbar>
        </div>
      </div>
    </>
  );
};
