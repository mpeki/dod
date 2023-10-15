import { ForwardedRef, useEffect, useState } from "react";
import { Character } from "../../../types/character";
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

interface IProps {
  fetchCharHandler: (charId: string) => Promise<Character>;
  charId: string;
}

export const PrintCharacter = (props: IProps, ref: ForwardedRef<HTMLDivElement>) => {

  const { fetchCharHandler, charId } = props;
  const auth = useAuth();
  const username = auth.user?.profile?.name || "";
  const [character, setCharacter] = useState<Character>();

  useEffect(() => {
    fetchCharHandler(charId || "").then((character) => {
      setCharacter(character);
    });
  }, [charId, fetchCharHandler]);

  if (character == null || character.id == null || character.looks == null) {
    return <><p>Invalid character!</p></>;
  }
  return (
    <Container maxWidth="md">
      <Grid container spacing={2}>
        <Grid item xs={5}>
          <BaseTraitList baseTraits={character.baseTraits} />
          <Grid container direction={"row"} spacing={2}>
            <PrintBonusXpHp character={character} />
          </Grid>
          <PrintCharacterSkillList charId={character.id || ""} skills={character?.skills || {}}
                                   fetchCharHandler={fetchCharHandler} canRemoveSkill={false} isPrinting={true} />
        </Grid>
        <Grid item xs={7} style={{ marginTop: '10px' }}>
          <PrintCharacterInfo character={character} username={username} changeHandler={fetchCharHandler} />
          <PrintHeroDetails character={character} />
          <Grid container>
            <PrintStatusStats character={character} />
          </Grid>
          <Grid container style={{ marginTop: '15px' }}>
            <PrintPsycheStats character={character} />
          </Grid>

          <PrintMagicSchools character={character} />
          <PrintSpells character={character} />
        </Grid>
      </Grid>
      <div className="page-break" />
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <PrintCharacterBody parts={character.bodyParts}/>
          <PrintWeapons character={character}/>
          <Grid container spacing={2}>
            <PrintItems character={character} />
          </Grid>
        </Grid>
      </Grid>

    </Container>
  );
};
