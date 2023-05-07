import { useCallback, useEffect, useState } from "react";
import { CharacterService } from "../../services/character.service";
import { Character } from "../../types/character";
import { CharacterCard } from "./CharacterCard";
import { Button } from "../../UI/Button";
import { AddCharacter } from "./AddCharacter";
import { Container, Fab } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import Grid from "@mui/system/Unstable_Grid";


export const CharacterOverview = (): JSX.Element => {
  const [characters, setCharacters] = useState<Character[]>([]);
  const [showCreateCharacter, setShowCreateCharacter] = useState<boolean>()

  const fetchCharsHandler = useCallback(async () => {
    await CharacterService.getCharacters()
    .then((characters) => {
      setCharacters(characters);
    })
    .catch((e) => alert("Error fetching characters: " + e));
  }, []);

  useEffect(() => {
    fetchCharsHandler().then();
  }, [fetchCharsHandler]);


  const showCharacterHandler = () => {
    if(showCreateCharacter){
      setShowCreateCharacter(false);
    } else {
      setShowCreateCharacter(true)
    }
  }

  return (
    <>
      <h1>Character Overview</h1>
      {showCreateCharacter && <AddCharacter fetchCharactersHandler={fetchCharsHandler} onConfirm={showCharacterHandler} />}
      <Container >
        <Fab onClick={showCharacterHandler} hidden={showCreateCharacter} color="success" size="small" aria-label="add">
          <AddIcon />
        </Fab>
        <Grid container spacing={2}>
          {characters && characters.map((char: Character) => <CharacterCard key={char.id} character={char} fetchCharactersHandler={fetchCharsHandler}/>)}
        </Grid>
      </Container>

    </>
  );
};
