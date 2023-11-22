import React from 'react';
import { Avatar } from '@mui/material';
import { Character } from "../../types/character";
import peasantStatusImg from "../../img/peasant.png";
import noblesStatusImg from "../../img/noble.png";
import paupersStatusImg from "../../img/pauper.png";
import citizenStatusImg from "../../img/citizen.png";


interface IProps {
  character: Character;
  avatarSrc: string;  // Source for the main avatar image
}

export const CharacterAvatar = ({ avatarSrc, character } : IProps) => {
  const containerStyle: React.CSSProperties = {
    position: 'relative',
    display: 'inline-block',
  };

  const overlappingImageStyle: React.CSSProperties = {
    position: 'absolute',
    bottom: -4,
    right: -1,
    width: '40%',
    height: '40%',
    // borderRadius: '50%',
    // border: '2px solid white',
  };

  // Determine the source of the overlapping image based on the character's social status
  let overlapSrc = '';
  let overlapTitle = '';
  switch (character.socialStatus) {
    case 'PEASANT':
      overlapSrc = peasantStatusImg;
      overlapTitle = 'Peasant';
      break;
    case 'NOBLE':
      overlapSrc = noblesStatusImg;
      overlapTitle = 'Nobel';
      break;
    case 'DISPOSSESSED':
      overlapSrc = paupersStatusImg;
      overlapTitle = 'Pauper';
      break;
    case 'CITIZEN':
      overlapSrc = citizenStatusImg;
      overlapTitle = 'Citizen';
      break;
    default:
      overlapSrc = ''; // Default or placeholder image
  }

  return (
    <div style={containerStyle}>
      <Avatar src={avatarSrc} >
        {character.name.substring(0, 1).toUpperCase()}
      </Avatar>
      <img src={overlapSrc} alt="Overlap" style={overlappingImageStyle} title={overlapTitle}/>
    </div>
  );
};

export default CharacterAvatar;
