import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import CharacterContext from "../../pages/Character/CharacterContext";
import { IconButton } from "@mui/material";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";

interface IProps {
  characterId?: string;
  inGutter: boolean;
}

export const DeleteCharacterButton = ({ characterId, inGutter } : IProps) => {

  const charContext = useContext(CharacterContext);
  const navigate = useNavigate();

  if (!charContext) {
    throw new Error("DeleteCharacterButton must be rendered within an ActivateCharContext.Provider");
  }
  const { deleteCharHandler, currentCharacter } = charContext;

  const handleDelete = () => {
    const deletePromise = characterId
      ? deleteCharHandler(characterId)
      : currentCharacter?.id
        ? deleteCharHandler(currentCharacter.id)
        : Promise.resolve();

    deletePromise.then(() => {
      navigate('/characters');  // Navigate to the new page
    });
  };

  if(inGutter) {
    return (
        <div style={{ marginTop: '5px' }}>
          <IconButton aria-label="delete" size="small" onClick={handleDelete} title="delete">
            <DeleteForeverIcon style={{ fontSize: '14px' }}/>
          </IconButton>
        </div>
    );
  } else {
    return (
        <IconButton aria-label="delete" onClick={handleDelete} title="delete">
          <DeleteForeverIcon/>
        </IconButton>
    );
  }
}