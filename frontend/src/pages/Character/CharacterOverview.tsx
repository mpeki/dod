import React, { useContext, useEffect, useState } from "react";
import { Character } from "../../types/character";
import { CharacterCard } from "./CharacterCard";
import { AddCharacter } from "./AddCharacter";
import { Container, Fab, Paper, Stack, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import Grid from "@mui/system/Unstable_Grid";
import charCreationImg from "../../img/new_character.png";
import { EmptyCharacterCard } from "./EmptyCharacterCard";
import withFlashing from "../../components/withFlashing";
import CharacterContext from "../Character/CharacterContext";


const styles = {
  characterContainer: {
    width: "100%",
    height: "100%",
    minWidth: 1200,
    maxWidth: 1200,
    backgroundImage: `url(${charCreationImg})`,
    backgroundPosition: "center",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    padding: 20
  }
};

export const CharacterOverview = (): React.JSX.Element => {

  // const [characters, setCharacters] = useState<Character[]>([]);
  const [showCreateCharacter, setShowCreateCharacter] = useState<boolean>();

  const FlashingFab = withFlashing(Fab);
  const charContext = useContext(CharacterContext);

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { activateCharHandler, fetchCharsHandler, characters } = charContext;
  const characterCount = characters.length;

  useEffect(() => {
    console.log("Fetching characters ... no lie!");
    fetchCharsHandler().then();
  }, [fetchCharsHandler, activateCharHandler]);


  const showCharacterHandler = () => {
    if (showCreateCharacter) {
      setShowCreateCharacter(false);
    } else {
      setShowCreateCharacter(true);
    }
  };
  return (
      <Container disableGutters>
        {showCreateCharacter &&
          <AddCharacter fetchCharactersHandler={fetchCharsHandler} onConfirm={showCharacterHandler} />}
        <Paper elevation={20}>
          <Stack direction={"row"}>
            <Stack direction={"column"}>
              <Typography sx={{ p: 2 }} variant="h5" gutterBottom align={"justify"}>
                Character Overview
              </Typography>
              <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
                This is where you manage your characters. You can create new characters, delete old ones and activate
                them
                for play.
                Press the + button to create a new character.
              </Typography>
              <Grid container justifyContent="center" style={styles.characterContainer}>
                {characters && characters.map((char: Character) => <CharacterCard key={char.id} character={char} />)}
                {[...Array(10 - characters.length)].map((_, index) => (
                  <EmptyCharacterCard key={index}>
                    {(!showCreateCharacter && index === 0) && (
                      <FlashingFab onClick={showCharacterHandler} color="success" size="small"
                                   aria-label="add" skipflash={characterCount > 0}>
                        <AddIcon style={{ opacity: 1 }} />
                      </FlashingFab>
                    )}
                  </EmptyCharacterCard>
                ))}
              </Grid>
            </Stack>
          </Stack>
        </Paper>
      </Container>
  );
};
