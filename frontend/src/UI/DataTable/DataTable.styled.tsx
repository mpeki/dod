import { TableContainer } from '@mui/material';
import { KeyboardArrowDownRounded, KeyboardArrowRightRounded } from '@mui/icons-material';
import styled from 'styled-components';

interface IDataTable {
  tableBorder?: boolean;
  rowRightBorder?: boolean;
}

export const DataTable = styled(TableContainer)<IDataTable>`
  overflow: none;
  background-color: ${(props) => props.theme.colors.datatable.defaultBackground};
  margin-bottom: 2px;

  & table {
    ${(props) => props.tableBorder && 'border: 1px solid' + props.theme.colors.datatable.defaultBorder}
  }

  & table thead th {
    background-color: ${(props) => props.theme.colors.datatable.headerBackground};
    color: ${(props) => props.theme.colors.datatable.headerText};
    font-size: 12px;
    font-weight: 600;
    text-align: left;
    padding: 10px 18px;
    white-space: nowrap;
    border-top: 1px solid ${(props) => props.theme.colors.datatable.defaultBorder};
    ${(props) => props.rowRightBorder && 'border-right: 1px solid ' + props.theme.colors.datatable.defaultBorder};
    border-bottom: 0;
  }

  & table tbody td {
    color: ${(props) => props.theme.colors.text};
    font-size: 14px;
    font-weight: normal;
    text-align: left;
    padding: 5px 18px;
    white-space: nowrap;
    border-top: 1px solid ${(props) => props.theme.colors.datatable.defaultBorder};
    ${(props) => props.rowRightBorder && 'border-right: 1px solid ' + props.theme.colors.datatable.defaultBorder};
    border-bottom: 0;
  }

  & table tfoot td {
    border-top: 1px solid ${(props) => props.theme.colors.datatable.defaultBorder};
    border-bottom: 0;
    padding: 0;
  }

  & table tr td.expandable {
    padding-bottom: 0;
    padding-top: 0;
    border-top: 0;
    border-bottom: 0;
  }

  & table tr.expanded {
    background-color: ${(props) => props.theme.colors.datatable.highlight};
  }
`;

export const RowCollapsedIcon = styled(KeyboardArrowRightRounded)`
  color: ${(props) => props.theme.colors.datatable.rowCollapsedIcon};
`;

export const RowExpandedIcon = styled(KeyboardArrowDownRounded)`
  color: ${(props) => props.theme.colors.datatable.rowExpandedIcon};
`;
