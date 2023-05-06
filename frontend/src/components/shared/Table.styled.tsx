import styled from "styled-components";
import { TableRow } from "@mui/material";

export const StyledTable = styled.div`
  color: ${(props) => props.theme.colors.text};

  .MuiTableRow-root {
    align-content: flex-start;  
  }
  .MuiTableCell-root {
    padding: 5px;
    padding-left: 10px;
    align-content: flex-start;
  }
`;

export const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));