import classes from "./BuySkill.module.css";
import { BuySkillForm } from "./BuySkillForm";
import { Character } from "../../types/character";

interface IProps {
  character: Character;
  onConfirm: any;
  buySkillHandler: any;
}

export const BuySkill = ({ character, buySkillHandler, onConfirm }: IProps) => {
  return <div>
    <div className={classes.backdrop} onClick={onConfirm}></div>
    <div className={classes.modal}>
      <header className={classes.header}>
        <h2>Buy Skills</h2>
      </header>
      <div className={classes.content}>
        <BuySkillForm character={character} buySkillHandler={buySkillHandler} onConfirm={onConfirm}/>
      </div>
      <footer className={classes.actions}>
      </footer>
    </div>
  </div>;
};
