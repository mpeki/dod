import React, { useEffect, useRef, useState } from "react";
import { ListItem, ListItemText, listItemTextClasses, TextField } from "@mui/material";
import ModeEditIcon from "@mui/icons-material/ModeEdit";

interface ListItemProps {
  label: string;
  value: string;
  nameChangeHandler: (name: string) => void;
  allowEdit: boolean;
}

const listItemTextSecondarySX = {
  '&:hover .MuiListItemText-secondary': {
    color: 'blue'
  },
};

const EditableListItem: React.FC<ListItemProps> = ({ label, value, nameChangeHandler, allowEdit }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [textValue, setTextValue] = useState(value);
  const [originalValue, setOriginalValue] = useState(value);
  const inputRef = useRef<HTMLInputElement>(null);
  const [inputError, setInputError] = useState(false);
  const validNameRegExp = /^[\p{L}]{2}([- ]?[\p{L}])*$/u;

  const handleClick = () => {
    setIsEditing(true);
  };

  const handleBlur = () => {
    setIsEditing(false);
    const inputValue = inputRef.current?.value || "";
    // Regular expression to validate the entered text
    const isValid = validNameRegExp.test(inputValue?.trim());

    if (isValid) {
      setTextValue(inputValue?.trim());
      nameChangeHandler(inputValue?.trim());
    } else {
      setTextValue(originalValue);
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      setIsEditing(false);
      inputRef.current?.blur();
    } else if (event.key === "Escape") {
      setIsEditing(false);
      setTextValue(originalValue);
      inputRef.current?.blur();
    }
  };
  const handleChange = () => {
    const inputValue = inputRef.current?.value || "";
    const isValid = validNameRegExp.test(inputValue?.trim());
    if (isValid) {
      setInputError(false);
    } else {
      setInputError(true);
    }
    setTextValue(inputValue);
  };

  const handleFocus = () => {
    setOriginalValue(textValue);
    if (inputRef.current) {
      inputRef.current.select();
    }
  };

  useEffect(() => {
    if (isEditing && inputRef.current) {
      inputRef.current.select();
    }
  }, [isEditing]);

  return (
    <ListItem component="div" onClick={handleClick} sx={listItemTextSecondarySX}>
      {allowEdit ? (

        isEditing ? (
          <TextField
            required
            fullWidth
            value={textValue}
            onChange={handleChange}
            onBlur={handleBlur}
            onKeyDown={handleKeyDown}
            inputRef={inputRef}
            autoFocus
            onFocus={handleFocus}
            error={inputError}
            helperText={inputError ? "Must be at least 2 alphabetic characters and no numbers, 2Pack!" : ""}
            variant="standard"
            label="Name"
          />
        ) : (
          <>
            <ListItemText primary={<React.Fragment>{label}<ModeEditIcon
              sx={{ marginLeft: "2px", fontSize: 12 }} /></React.Fragment>} secondary={textValue} />
          </>
        )) : (
        <ListItemText primary={label} secondary={textValue} />
      )}
    </ListItem>
  );
};

export default EditableListItem;
