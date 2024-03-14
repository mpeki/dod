import { BodyPartValue, Character } from "../../../../types/character";
import { useTranslation } from "react-i18next";
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
import { StyledTable } from "../../../../components/shared/Table.styled";
import { StyledList } from "../../../../components/shared/List.styled";
import AddIcon from "@mui/icons-material/Add";
import { BodyPartItem } from "../../BodyPart";

interface IProps {
  parts: Record<string, BodyPartValue> | undefined;
}

export const PrintCharacterBody = ({ parts } : IProps) => {

  const { t } = useTranslation("char");

  if (parts) {
    const bodyPartMap: Map<string, BodyPartValue> = new Map(Object.entries(parts));
    const bodyParts = function(): any[] {
      const items = [];
      for (let [key, value] of bodyPartMap) {
        if (key === "TOTAL") continue;
        items.push(<BodyPartItem key={key} bodyPartName={key} bodyPartValue={value} item={undefined} />);
      }
      return items;
    };

    return (
      <>
        <StyledList style={{ marginTop: '25px' }}>
          <ListItem dense={true}>
            <ListItemText primary={t("detail.body.totalHP")} secondary={parts["TOTAL"].maxHP} />
          </ListItem>
        </StyledList>
        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: 'bold' }} colSpan={3} align="center">{t("detail.body.header.hp")}</TableCell>
                <TableCell sx={{ fontWeight: 'bold' }} colSpan={4} align="justify">{t("detail.body.header.armor")}</TableCell>
              </TableRow>
            </TableHead>
            <TableHead>
              <TableRow>
                <TableCell sx={{ borderBottom: 1, borderRight: 1, borderColor: "darkgray", fontWeight: 'bold' }}>{t("detail.body.header.bodyPart")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.maxHP")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.currentHP")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.armorMat")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.abs")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.weight")}</TableCell>
                <TableCell sx={{ borderBottom: 1, borderRight: 0, borderColor: "darkgray"}}>{t("detail.body.header.penalty")}</TableCell>
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
}