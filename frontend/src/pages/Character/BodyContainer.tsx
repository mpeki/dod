import { BodyPartValue } from "../../types/character";
import { BodyPartItem } from "./BodyPart";
import { StyledTable } from "../../components/shared/Table.styled";
import {
  Fab,
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
import AddIcon from "@mui/icons-material/Add";
import { useTranslation } from "react-i18next";

interface IProps {
  parts: Record<string, BodyPartValue> | undefined;
}

export const BodyContainer = ({ parts }: IProps): JSX.Element => {
  const { t } = useTranslation("char");

  if (parts) {
    const bodyPartMap: Map<string, BodyPartValue> = new Map(Object.entries(parts));
    const bodyParts = function(): any[] {
      const items = [];
      for (let [key, value] of bodyPartMap) {
        if (key === "TOTAL") continue;
        items.push(<BodyPartItem key={key} bodyPartName={key} bodyPartValue={value} />);
      }
      return items;
    };

    return (
      <>
        <List dense={true}>
          <StyledList>
            <ListItem dense={true}>
              <ListItemText primary={t("detail.body.totalHP")} secondary={parts["TOTAL"].maxHP} />
            </ListItem>
            <IconButton edge="start" aria-label="add skill">
              <AddIcon />
            </IconButton>
          </StyledList>
        </List>

        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell colSpan={3} align="center">{t("detail.body.header.hp")}</TableCell>
                <TableCell colSpan={4} align="justify">{t("detail.body.header.armor")}</TableCell>
              </TableRow>
            </TableHead>
            <TableHead>
              <TableRow>
                <TableCell>{t("detail.body.header.hp")}</TableCell>
                <TableCell>{t("detail.body.header.maxHP")}</TableCell>
                <TableCell>{t("detail.body.header.currentHP")}</TableCell>
                <TableCell>{t("detail.body.header.armorMat")}</TableCell>
                <TableCell>{t("detail.body.header.abs")}</TableCell>
                <TableCell>{t("detail.body.header.weight")}</TableCell>
                <TableCell>{t("detail.body.header.penalty")}</TableCell>
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