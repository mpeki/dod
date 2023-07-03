import { useCallback, useEffect, useState } from 'react';
import { createTheme, Theme } from '@mui/material/styles';
import axios from 'axios';

declare module '@mui/material/styles/createTheme' {
  export interface Theme {
    name: string;
    colors: {
      bodyBackground: string;
      componentBackground: string;
      componentBackgroundHidden: string;
      tileBackground: string;
      text: string;
      inactiveText: string;
      infoOutlinedIconFill: string;
      placeholderText: string;
      boxShadow: string;
      dialogBoxShadow: string;
      componentBorder: string;
      componentBorderHover: string;
      separator: string;
      tooltipBackground: string;
      tooltipText: string;
      navigationLink: string;
      navigationLinkHover: string;
      calendarBackground: string;
      calendarBackgroundHover: string;
      calendarBackgroundClicked: string;
      buttonBackground: string;
      hoverButtonBackground: string;
      primaryButtonBackground: string;
      primaryButtonColor: string;
      expandCollapseBorder: string;
      selectDropdownHover: string;
      selectTextColor: string;
      radioGroupCheckedColor: string;
      resetButtonText: string;
      selectDropdownArrow: string;
      selectedTab: string;
      validations: {
        error: string;
        valid: string;
      };
      household: {
        primaryContactIconBackground: string;
        componentBackground: string;
        separator: string;
        selectedBackground: string;
        hoverBackground: string;
        inactiveText: string;
        text: string;
        textDefault: string;
        selectedItemText: string;
        selectedItemInactiveText: string;
        statusAccepted: string;
        statusRejected: string;
        statusPending: string;
        statusMissingData: string;
      };
      addHouseholdMember: {
        failedFindCustomer: string,
        failedFindCustomerBorder: string,
        notFoundCustomer: string,
        notFoundCustomerBorder: string
      };
      editHousehold: {
        statusTextClosed: string;
        statusBackgroundClosed: string;
        statusTextActive: string;
        statusBackgroundActive: string;
      };
      profiling: {
        boxHeader: string;
        boxOpen: string;
        boxOverdue: string;
        textOpen: string;
        textOverdue: string;
      };
      search: {
        button: string;
        resultHoverBackground: string;
        resultLabelCustomer: string;
        resultLabelNationalId: string;
        resultLabelCustomerId: string;
        resultLabelPhone: string;
        resultLabelCustomerBackground: string;
        resultLabelNationalIdBackground: string;
        resultLabelCustomerIdBackground: string;
        resultLabelPhoneBackground: string;
      };
    };
  }
}

export const useTheme = (): { currentTheme?: Theme; setActiveTheme: (themeName: string) => void } => {
  const [themes, setThemes] = useState<Theme[]>([]);
  const [currentTheme, setCurrentTheme] = useState<Theme>();
  const [themesLoaded, setThemesLoaded] = useState(false);
  const setActiveTheme = useCallback(
    (themeName: string): void => {
      const theme = themes.find((t) => t.name === themeName);

      if (!theme) {
        throw new Error(`Requested theme ${themeName} could not be found.`);
      }

      setCurrentTheme(createTheme(theme));
    },
    [themesLoaded]
  );

  useEffect(() => {
    axios
      .get('/themes.json')
      .then((response) => response.data)
      .then((data: { themes: Theme[] }) => {
        setThemes(data.themes);
        setThemesLoaded(true);
      })
      .catch(() => {
        throw new Error('Could not fetch themes.');
      });
  }, []);

  useEffect(() => {
    // eslint-disable-next-line @typescript-eslint/no-unused-expressions
    themesLoaded && !currentTheme ? setCurrentTheme(themes[0]) : '';
  }, [themesLoaded]);

  return { currentTheme, setActiveTheme };
};
