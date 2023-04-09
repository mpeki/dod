import classes from "./BuySkill.module.css";
import { BuySkillForm } from "./BuySkillForm";

interface IProps {
  charId: number;
  onConfirm: any;
  buySkillHandler: any;
}

export const BuySkill = ({ charId, buySkillHandler, onConfirm }: IProps) => {
  return <div>
    <div className={classes.backdrop} onClick={onConfirm}></div>
    <div className={classes.modal}>
      <header className={classes.header}>
        <h2>Buy Skills</h2>
      </header>
      <div className={classes.content}>
        <BuySkillForm charId={charId} buySkillHandler={buySkillHandler} onConfirm={onConfirm}/>
      </div>
      <footer className={classes.actions}>
      </footer>
    </div>
  </div>;
};
