import { Typography } from "@mui/material";
import HumanoidMaleSVG from '../../../img/humanoid_male.png';
import { BodyPartValue, Character } from "../../../types/character";
import { CSSProperties } from "react";

interface IProps {
  character: Character;
}

export const HumanoidComponent = ( { character } : IProps ) => {
  if (!character.bodyParts) {
    return <>Body parts data not available</>;
  }
  return (
    <div style={{ display: 'block', flexDirection: 'row', alignItems: 'end' }}>
      <img src={HumanoidMaleSVG} alt="Humanoid" style={{ width: 'auto', height: '150px', margin: 0, padding: 0 }} />
      <Typography style={getTypographyStyle('35%', '75%', character.bodyParts["HEAD"])}>{displayBodyPartHP(character, "HEAD")}</Typography>
      <Typography style={getTypographyStyle('50%', '75%', character.bodyParts["CHEST"])}>{displayBodyPartHP(character, "CHEST")}</Typography>
      <Typography style={getTypographyStyle('63%', '75%', character.bodyParts["STOMACH"])}>{displayBodyPartHP(character, "STOMACH")}</Typography>
      <Typography style={getTypographyStyle('60%', '90%', character.bodyParts["LEFT_ARM"])}>{displayBodyPartHP(character, "LEFT_ARM")}</Typography>
      <Typography style={getTypographyStyle('60%', '62%', character.bodyParts["RIGHT_ARM"])}>{displayBodyPartHP(character, "RIGHT_ARM")}</Typography>
      <Typography style={getTypographyStyle('85%', '81%', character.bodyParts["LEFT_LEG"])}>{displayBodyPartHP(character, "LEFT_LEG")}</Typography>
      <Typography style={getTypographyStyle('85%', '70%', character.bodyParts["RIGHT_LEG"])}>{displayBodyPartHP(character, "RIGHT_LEG")}</Typography>
      {/* Position other hit points similarly */}
    </div>
  );

  function displayBodyPartHP(character: Character, part: string): string {
    if (!character.bodyParts) {
      return "Body parts data not available";
    }

    const bodyPart = character.bodyParts[part];
    if (!bodyPart) {
      return `N/A`;
    }
    return `${bodyPart.currentHP}`;
  }

  function getTypographyStyle(top: string, left: string, bodyPart: BodyPartValue): CSSProperties {
    if (!character.bodyParts) {
      return {};
    }
    // const bodyPart = character.bodyParts[part];
    const color = bodyPart.currentHP >= bodyPart.maxHP ? 'green' : bodyPart.currentHP <= 0 ?  'red' : 'orange';

    return {
      position: 'absolute',
      top: top,
      left: left,
      backgroundColor: 'white',
      color: color,
      padding: '1px 3px',
      borderRadius: '5px',
      boxShadow: '0px 0px 5px rgba(0, 0, 0, 0.2)',
      fontSize: '12px',
    };
  }
}
