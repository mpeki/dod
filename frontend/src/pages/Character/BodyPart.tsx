import { BodyPartValue } from "../../types/character";
import { TableCell, TableRow } from "@mui/material";
import { useTranslation } from "react-i18next";

interface IProps {
  bodyPartName: string;
  bodyPartValue: BodyPartValue | undefined;
}

export const BodyPartItem = ({ bodyPartName, bodyPartValue }: IProps) => {
  const { t } = useTranslation("char");
  return (
    <TableRow key={bodyPartName} >
      <TableCell>{t(`detail.body.part.${bodyPartName}`)}</TableCell>
      <TableCell>{bodyPartValue?.maxHP}</TableCell>
      <TableCell>{bodyPartValue?.currentHP} </TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
      <TableCell></TableCell>
    </TableRow>
  );
};