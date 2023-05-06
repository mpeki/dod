import { Menu, MenuItem } from '@mui/material';
import { KeyboardArrowLeftRounded, KeyboardArrowRightRounded } from '@mui/icons-material';
import styled from 'styled-components';

export const StyledPagination = styled.ul`
  display: inline-block;
  list-style: none;
`;

export const StyledPaginationItem = styled.li`
  color: ${({ theme }) => theme.colors.datatable.page};
  background-color: transparent;
  display: inline;
  margin-right: 3px;
  list-style: none;
  cursor: pointer;
  border-radius: 6px;

  &:not(.prev):not(.next):not(.more-pages) {
    padding: 7px 12px;
  }

  &:hover:not(.selected):not(.prev):not(.next):not(.more-pages) {
    background-color: ${({ theme }) => theme.colors.datatable.pageHoverBackground};
  }

  &.selected {
    color: ${({ theme }) => theme.colors.datatable.pageSelected};
    background-color: ${({ theme }) => theme.colors.datatable.pageSelectedBackground};
    cursor: none;
  }

  &.more-pages {
    padding: auto;
    margin: auto;
  }

  &.more-pages span {
    display: inline-block;
    width: 32px;
    height: 28px;
  }

  &.more-pages:hover {
    color: ${({ theme }) => theme.colors.datatable.pageArrowHover};
    font-weight: bold;
  }
`;

export const PaginationDropdown = styled(Menu)`
  & div {
    color: ${({ theme }) => theme.colors.datatable.page};
    background-color: ${({ theme }) => theme.colors.datatable.dropDownBackground};
    box-shadow: ${({ theme }) => theme.colors.datatable.boxShadow};
    border-radius: 6px;

    scrollbar-color: ${(props) => props.theme.colors.scrollbarThumb}
      ${(props) => props.theme.colors.datatable.dropDownBackground};
    scrollbar-width: thin;

    ::-webkit-scrollbar {
      width: 6px;
    }

    ::-webkit-scrollbar-thumb {
      background-color: ${(props) => props.theme.colors.scrollbarThumb};
      border-radius: 10px;
    }

    ::-webkit-scrollbar-track {
      background-color: ${(props) => props.theme.colors.datatable.dropDownBackground};
    }

    /*IE*/
    @media screen and (-ms-high-contrast: active), (-ms-high-contrast: none) {
      scrollbar-base-color: ${(props) => props.theme.colors.datatable.dropDownBackground};
      scrollbar-face-color: ${(props) => props.theme.colors.scrollbarThumb};
      scrollbar-3dlight-color: ${(props) => props.theme.colors.scrollbarThumb};
      scrollbar-highlight-color: ${(props) => props.theme.colors.scrollbarThumb};
      scrollbar-track-color: ${(props) => props.theme.colors.datatable.dropDownBackground};
      scrollbar-arrow-color: ${(props) => props.theme.colors.scrollbarThumb};
      scrollbar-shadow-color: ${(props) => props.theme.colors.scrollbarThumb};
      scrollbar-dark-shadow-color: ${(props) => props.theme.colors.scrollbarThumb};
    }
  }
`;

export const PaginationDropdownItem = styled(MenuItem)`
  && {
    font-size: 0.75rem;
  }
  &&:hover {
    background-color: ${({ theme }) => theme.colors.datatable.pageHoverBackground};
  }
`;

export const PrevPageIcon = styled(KeyboardArrowLeftRounded)`
  color: ${({ theme }) => theme.colors.datatable.pageArrow};

  &:hover:not(.disabled) {
    color: ${({ theme }) => theme.colors.datatable.pageArrowHover};
  }

  &.disabled {
    color: ${({ theme }) => theme.colors.datatable.pageArrowDisabled};
  }
`;

export const NextPageIcon = styled(KeyboardArrowRightRounded)`
  color: ${({ theme }) => theme.colors.datatable.pageArrow};

  &:hover:not(.disabled) {
    color: ${({ theme }) => theme.colors.datatable.pageArrowHover};
  }

  &.disabled {
    color: ${({ theme }) => theme.colors.datatable.pageArrowDisabled};
  }
`;
