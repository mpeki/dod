import { BaseTraitValue } from "../../types/character";
import { BaseTraitItem } from "./BaseTraitItem";
import { Table, TableBody, TableHead, TableRow, TableCell } from "@mui/material";
import { StyledTable } from "../shared/Table.styled";

interface IProps {
  baseTraits: Record<string, BaseTraitValue> | undefined;
}

export const BaseTraitList = ({ baseTraits }: IProps): JSX.Element => {

  if (baseTraits) {
    const baseTraitMap: Map<string, BaseTraitValue> = new Map(Object.entries(baseTraits));
    const baseTraitItems = function(): any[] {
      const items = [];
      for (let [key, value] of baseTraitMap) {
        items.push(<BaseTraitItem key={key} baseTraitName={key} itemValues={value} />);
      }
      return items;
    };

    return (
      <>
        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell></TableCell>
                <TableCell>Value</TableCell>
                <TableCell>Group</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {baseTraitItems()}
            </TableBody>
          </Table>
        </StyledTable>
      </>
    );
  } else return (<></>);
};