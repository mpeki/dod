import { BaseTraitValue } from "../../types/character";
import { TableRow, TableCell } from '@mui/material';

interface IProps {
  baseTraitName: string;
  itemValues: BaseTraitValue | undefined;
}

export const BaseTraitItem = ({ baseTraitName, itemValues }: IProps) => {
  return (
    <TableRow key={baseTraitName} >
      <TableCell colSpan={2} align="justify" sx={{ fontWeight: 'bold' }}>{baseTraitName}</TableCell>
      <TableCell colSpan={2} align="right">{itemValues?.currentValue}</TableCell>
      <TableCell colSpan={2} align="right" padding={"checkbox"}>{itemValues?.groupValue}</TableCell>
      <TableCell colSpan={3}></TableCell>
    </TableRow>
  );
};