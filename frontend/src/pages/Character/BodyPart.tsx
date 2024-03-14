import { BodyPartValue } from "../../types/character";
import { TableCell, TableRow } from "@mui/material";
import { useTranslation } from "react-i18next";
import { CharacterItem } from "../../types/item";

interface IProps {
  bodyPartName: string;
  bodyPartValue: BodyPartValue | undefined;
  item: CharacterItem | undefined;
}

export const BodyPartItem = ({ bodyPartName, bodyPartValue, item }: IProps) => {
  const { t } = useTranslation(["char", "items"]);
  return (
    <TableRow key={bodyPartName} >
      <TableCell sx={{ borderRight: 1, borderBottom: 1, borderRightColor: "darkgray", borderBottomColor: "lightgray" }}>
          {t(`char:detail.body.part.${bodyPartName}`)}
      </TableCell>
      <TableCell>{bodyPartValue?.maxHP}</TableCell>
      <TableCell>{bodyPartValue?.currentHP} </TableCell>
      <TableCell>{item && t(`items:materials.${item?.item.itemKey.substring(0, item?.item.itemKey.indexOf("."))}`)}</TableCell>
      <TableCell>{item?.item.abs}</TableCell>
      <TableCell>{item?.currentWeight ? item.currentWeight : item?.item.weight}</TableCell>
      <TableCell>{item?.item.dodgePenalty}</TableCell>
    </TableRow>
  );
};