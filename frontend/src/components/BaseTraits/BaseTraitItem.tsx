import { BaseTraitValue } from "../../types/character";
import { TableRow, TableCell } from '@mui/material';

interface IProps {
  baseTraitName: string;
  itemValues: BaseTraitValue | undefined;
}

export const BaseTraitItem = ({ baseTraitName, itemValues }: IProps) => {
  return (
    <TableRow key={baseTraitName} >
      <TableCell>{baseTraitName}</TableCell>
      <TableCell>{itemValues?.currentValue}</TableCell>
      <TableCell>{itemValues?.groupValue}</TableCell>
    </TableRow>
  );
};