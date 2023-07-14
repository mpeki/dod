import { useCallback, useEffect, useState } from "react";
import { CharacterService } from "../../services/character.service";
import { Character } from "../../types/character";
import { CharacterCard } from "./CharacterCard";
import { AddCharacter } from "./AddCharacter";
import { Container, Fab, Paper } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import Grid from "@mui/system/Unstable_Grid";
import charCreationImg from "../../img/new_character.png";

const styles = {
  characterContainer: {
    width: "100%",
    height: "100%",
    backgroundImage: `url(${charCreationImg})`,
    backgroundPosition: "center",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
  }
};

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
    <Container disableGutters>
      {showCreateCharacter && <AddCharacter fetchCharactersHandler={fetchCharsHandler} onConfirm={showCharacterHandler} />}
      <Paper style={styles.characterContainer} elevation={3} sx={{ p: 5 }}>
        <Fab onClick={showCharacterHandler} hidden={showCreateCharacter} color="success" size="small" aria-label="add" disabled={showCreateCharacter}>
          <AddIcon />
        </Fab>
        <Grid container spacing={2}>
          {characters && characters.map((char: Character) => <CharacterCard key={char.id} character={char} fetchCharactersHandler={fetchCharsHandler}/>)}
        </Grid>
      </Paper>
    </Container>
  );
};
