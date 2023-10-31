import { ForwardedRef, useContext, useEffect, useState } from "react";
import { Container, Grid } from "@mui/material";
import { BaseTraitList } from "../../../components/BaseTraits/BaseTraitList";
import { PrintCharacterInfo } from "./pageOne/PrintCharacterInfo";
import { useAuth } from "react-oidc-context";
import { PrintCharacterSkillList } from "./pageOne/PrintCharacterSkillList";
import { PrintHeroDetails } from "./pageOne/PrintHeroDetails";
import { PrintPsycheStats } from "./pageOne/PrintPsycheStats";
import { PrintStatusStats } from "./pageOne/PrintStatusStats";
import { PrintSpells } from "./pageOne/PrintSpells";
import { PrintMagicSchools } from "./pageOne/PrintMagicSchools";
import { PrintBonusXpHp } from "./pageOne/PrintBonusXpHp";
import { PrintCharacterBody } from "./pageTwo/PrintCharacterBody";
import { PrintWeapons } from "./pageTwo/PrintWeapons";
import { PrintItems } from "./pageTwo/PrintItems";
import CharacterContext from "../CharacterContext";


export const PrintCharacter = () => {

  const auth = useAuth();
  const username = auth.user?.profile?.name || "";
  const charContext = useContext(CharacterContext);
  if (!charContext || !charContext.currentCharacter) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { currentCharacter } = charContext;

  if (currentCharacter == null || currentCharacter.id == null || currentCharacter.looks == null) {
    return <><p>Invalid character!</p></>;
  }
  return (
    <Container maxWidth="md">
      <Grid container spacing={2}>
        <Grid item xs={5}>
          <BaseTraitList baseTraits={currentCharacter.baseTraits} />
          <Grid container direction={"row"} spacing={2}>
            <PrintBonusXpHp character={currentCharacter} />
          </Grid>
          <PrintCharacterSkillList skills={currentCharacter?.skills || {}}
                                   canRemoveSkill={false} isPrinting={true} />
        </Grid>
        <Grid item xs={7} style={{ marginTop: '10px' }}>
          <PrintCharacterInfo character={currentCharacter} username={username}  />
          <PrintHeroDetails character={currentCharacter} />
          <Grid container>
            <PrintStatusStats character={currentCharacter} />
          </Grid>
          <Grid container style={{ marginTop: '15px' }}>
            <PrintPsycheStats character={currentCharacter} />
          </Grid>

          <PrintMagicSchools character={currentCharacter} />
          <PrintSpells character={currentCharacter} />
        </Grid>
      </Grid>
      <div className="page-break" />
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <PrintCharacterBody parts={currentCharacter.bodyParts}/>
          <PrintWeapons character={currentCharacter}/>
          <Grid container spacing={2}>
            <PrintItems character={currentCharacter} />
          </Grid>
        </Grid>
      </Grid>

    </Container>
  );
};
