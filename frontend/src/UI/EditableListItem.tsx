import React, { useEffect, useRef, useState } from "react";
import { ListItem, ListItemText, TextField } from "@mui/material";
import ModeEditIcon from "@mui/icons-material/ModeEdit";

interface ListItemProps {
  label: string;
  value: string;
  changeType: string;
  changeHandler: (changeKey: string, mod: any) => void;
  allowEdit: boolean;
  validationPattern?: RegExp;
  validationErrorMsg?: string;
}

const listItemTextSecondarySX = {
  '&:hover .MuiListItemText-secondary': {
    textDecoration: 'underline',
  },
};

const EditableListItem: React.FC<ListItemProps> = ({ label, value, changeType, changeHandler, allowEdit, validationPattern, validationErrorMsg }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [textValue, setTextValue] = useState(value);
  const [originalValue, setOriginalValue] = useState(value);
  const inputRef = useRef<HTMLInputElement>(null);
  const [inputError, setInputError] = useState(false);
  const validNameRegExp = validationPattern || /(?:)/;

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
      changeHandler(changeType, inputValue?.trim());
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
    <ListItem component="div" onClick={handleClick} sx={allowEdit ? listItemTextSecondarySX : {}}>
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
            helperText={inputError ? validationErrorMsg : ""}
            variant="standard"
            label={label}
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
