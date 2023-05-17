import { BodyPartValue } from "../../types/character";
import { BodyPartItem } from "./BodyPart";
import { StyledTable } from "../../components/shared/Table.styled";
import {
  IconButton,
  List,
  ListItem,
  ListItemText,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@mui/material";
import { StyledList } from "../../components/shared/List.styled";
import KeyboardDoubleArrowDownIcon from "@mui/icons-material/KeyboardDoubleArrowDown";
import AddIcon from "@mui/icons-material/Add";

interface IProps {
  parts: Record<string, BodyPartValue> | undefined;
}

export const BodyContainer = ({ parts }: IProps): JSX.Element => {

  if (parts) {
    const bodyPartMap: Map<string, BodyPartValue> = new Map(Object.entries(parts));
    const bodyParts  = function(): any[] {
      const items = []
      for(let [key,value] of bodyPartMap ){
        items.push(<BodyPartItem key={key} bodyPartName={key} bodyPartValue={value} />)
      }
      return items;
    }

    return (
      <>
        <List dense={true}>
          <StyledList>
            <ListItem dense={true}>
              <ListItemText primary="Total HP" secondary={parts['TOTAL'].maxHP} />
            </ListItem>
          </StyledList>
        </List>

        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell colSpan={3} align='center'>Hit Points</TableCell>
                <TableCell colSpan={4} align='justify'>Armor</TableCell>
              </TableRow>
            </TableHead>
            <TableHead>
              <TableRow>
                <TableCell>Body Part</TableCell>
                <TableCell>Max</TableCell>
                <TableCell>Current</TableCell>
                <TableCell>Material</TableCell>
                <TableCell>Abs</TableCell>
                <TableCell>Weight</TableCell>
                <TableCell>Pen</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {bodyParts()}
            </TableBody>
          </Table>
        </StyledTable>
      </>
    );
  } else return (<></>);
};