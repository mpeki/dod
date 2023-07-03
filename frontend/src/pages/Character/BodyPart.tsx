import { BodyPartValue } from "../../types/character";
import { TableCell, TableRow } from "@mui/material";

interface IProps {
  bodyPartName: string;
  bodyPartValue: BodyPartValue | undefined;
}

export const BodyPartItem = ({ bodyPartName, bodyPartValue }: IProps) => {
  return (
    <TableRow key={bodyPartName} >
      <TableCell>{bodyPartName}</TableCell>
      <TableCell>{bodyPartValue?.maxHP}</TableCell>
      <TableCell>{bodyPartValue?.currentHP} </TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
    </TableRow>
  );
};