import { Theme } from '@mui/material';
import { createGlobalStyle, ThemeProps } from 'styled-components';

/**
 * In order to keep App.tsx clean, global styles, Material UI overrides need to be here:
 * */

export const GlobalStyles = createGlobalStyle`
  body {
    background-color: ${(props: ThemeProps<Theme>) => props.theme.colors.bodyBackground};
    color: ${(props: ThemeProps<Theme>) => props.theme.colors.text};
    margin: 0;
    font-size: 14px;
  }

  body * {
    font-family: Inter, serif !important;
  }

  // ----- Scrollbar in application: -----
  ::-webkit-scrollbar {
    width: 6px;
  }

  ::-webkit-scrollbar-thumb {
    background: #c2ccc4;
    border-radius: 10px;
  }

  // ----- POPOVER ------
  div.MuiPopover-paper {
    margin-top: 10px !important;
  }

  // ----- DIALOG ------

  div.MuiDialogContent-dividers {
    border-top: 1px solid ${(props: ThemeProps<Theme>) => props.theme.colors.separator};
    border-bottom: 1px solid ${(props: ThemeProps<Theme>) => props.theme.colors.separator};
  }

  button {
    padding: 6px 8px;

    font-style: normal;
    font-weight: 500;
    font-size: 12px;
    line-height: 18px;

    text-align: center;
    text-overflow: ellipsis;

    color: ${(props) => props.theme.colors.text};
    background: ${(props) => props.theme.colors.buttonBackground};

    box-shadow: 0 1px 1px rgba(0, 40, 59, 0.25), 0 0 1px rgba(0, 40, 59, 0.31);
    border-radius: 6px;
    border: 0;
    outline: none;

    &:hover {
      background: ${(props) => props.theme.colors.hoverButtonBackground};
    }
  }

  button.primary {
    cursor: pointer;
    color: ${(props) => props.theme.colors.primaryButtonColor};
    background: ${(props) => props.theme.colors.primaryButtonBackground};
  }

  // Material Input modifications
  div.MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline, div.MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline {
    border-color: inherit;
    border: 0px;
  }

  div.MuiTextField-root {
    //min-height: 62px;

    .MuiOutlinedInput-root {
      border-radius: 6px;
      border: 1px solid ${(props: ThemeProps<Theme>) => props.theme.colors.componentBorder};

      &.Mui-error {
        border: 1px solid ${(props) => props.theme.colors.validations.error} !important;
      }

      &:hover,
      &:focus-within {
        border: 1px solid ${(props: ThemeProps<Theme>) => props.theme.colors.componentBorderHover};

        & .MuiOutlinedInput-notchedOutline {
          border: 0;
        }
      }
    }

    div.MuiOutlinedInput-input, input.MuiOutlinedInput-input {
      padding: 10px 10px 10px 12px;
      color: ${(props) => props.theme.colors.text};
    }

    & > p.MuiFormHelperText-contained {
      margin-left: 0;
      font-family: inherit;
      font-size: 13px;
      line-height: 16px;
      font-weight: normal;
      color: ${(props) => props.theme.colors.validations.valid};

      &.Mui-error {
        color: ${(props) => props.theme.colors.validations.error};
      }
    }
  }

  // ----- Google maps autocomplete -----
  .pac-container {
    z-index: 10000;
  }

  // ----- Material Dialog modifications -----
  div.MuiDialogContent-dividers {
    padding: 0;

    // ----- Form control label and labels: -----
    label.MuiFormControlLabel-root {
      span.MuiTypography-root {
        font-size: 14px;
      }

    ,
    }

  ,
  }

  div.MuiDialogActions-root {
    padding: 16px 24px;
    border-top: 1px solid ${(props: ThemeProps<Theme>) => props.theme.colors.componentBorder};
  }

  // ----- Select dropdown: -----
  & div.MuiPopover-paper {
    margin-top: 50px;

    & ul.MuiList-root {
      background: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
    }

    li.MuiButtonBase-root {
      color: ${(props: ThemeProps<Theme>) => props.theme.colors.text};

      &:hover {
        color: ${(props: ThemeProps<Theme>) => props.theme.colors.selectTextColor};
        background: ${(props: ThemeProps<Theme>) => props.theme.colors.selectDropdownHover};
      }
    }
  }

  // ----- Material-ui Tabs : -----
  span.MuiTabs-indicator {
    background-color: ${(props: ThemeProps<Theme>) => props.theme.colors.selectedTab};
  }

  button.MuiTab-root {
    &.Mui-selected {
      color: ${(props: ThemeProps<Theme>) => props.theme.colors.selectedTab}
    }
  }

  // ----- Autocomplete component: -----

  div.MuiAutocomplete-endAdornment {
    svg.MuiSvgIcon-root {
      background-color: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
    }

    .MuiAutocomplete-clearIndicator {
      background-color: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
    }

    span.MuiTouchRipple-root {
      color: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
    }

    span.MuiIconButton-label {
      color: grey;

      &:hover {
        background: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
      }
    }

    button.MuiButtonBase-root {
      background: ${(props: ThemeProps<Theme>) => props.theme.colors.componentBackground};
      box-shadow: none;
    }

    .MuiAutocomplete-popupIndicator {
      padding: 0;
      margin: 0;
    }
  }
`;
