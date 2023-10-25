import classes from "./BuySkill.module.css";
import { BuySkillForm } from "./BuySkillForm";
import { Character } from "../../types/character";

interface IProps {
  character: Character;
  onConfirm: any;
}

export const BuySkill = ({ character, onConfirm }: IProps) => {
  return <div>
    <div className={classes.backdrop} onClick={onConfirm}></div>
    <div className={classes.modal}>
      <header className={classes.header}>
        <h2>Buy Skills</h2>
      </header>
      <div className={classes.content}>
        <BuySkillForm character={character} onConfirm={onConfirm}/>
      </div>
      <footer className={classes.actions}>
      </footer>
    </div>
  </div>;
};
