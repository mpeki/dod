import { TextField } from '@mui/material';
import React, { FC } from 'react';
import { StyledSearchBox } from './SearchBox.styled';

interface SearchBoxProps {
  onInput: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  style?: React.CSSProperties;
}

const SearchBox: FC<SearchBoxProps> = ({ onInput, placeholder, style }: SearchBoxProps) => {
  return (
    <StyledSearchBox style={style}>
      <TextField type="search" onInput={onInput} placeholder={placeholder} InputProps={{ disableUnderline: true }} />
    </StyledSearchBox>
  );
};

export default SearchBox;
