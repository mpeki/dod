import classes from "./AddCharacter.module.css";
import { CreateCharacterForm } from "./CreateCharacterForm";
import { KeyboardShortcutProvider } from "../../components/KeyboardShortcutProvider";

interface IProps {
  onConfirm: any;
  fetchCharactersHandler: any;
}

export const AddCharacter = ({ onConfirm, fetchCharactersHandler }: IProps) => {

  const shortcut = [{ key: "Escape", callback: () => onConfirm() }];


  return (
    <KeyboardShortcutProvider shortcuts={shortcut}>
      <div>
        <div className={classes.backdrop} onClick={onConfirm}></div>
        <div className={classes.modal}>
          <header className={classes.header}>
            <h2>Create New Character</h2>
          </header>
          <div className={classes.content}>
            <CreateCharacterForm fetchCharactersHandler={fetchCharactersHandler} onConfirm={onConfirm} />
          </div>
          <footer className={classes.actions}>
          </footer>
        </div>
      </div>
    </KeyboardShortcutProvider>
  );};
