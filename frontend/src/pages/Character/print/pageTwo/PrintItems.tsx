import { Grid, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../../../components/shared/Table.styled";
import { useState } from "react";
import { Character } from "../../../../types/character";
import { Item } from "../../../../types/item";
import { useTranslation } from "react-i18next";

interface IProps {
  character: Character;
}


export const PrintItems = ({ character }: IProps) => {

    const { t } = useTranslation(["char", "items"]);
    const [items, setItems] = useState<Map<string, Item>>(new Map());

    const itemRows = () => {
      const result: JSX.Element[] = [];
      items.forEach((item, key) => {
        if (item.itemType !== "MELEE_WEAPON" && item.itemType !== "RANGED_WEAPON" && item.itemType !== "SHIELD") return;
        result.push(<TableRow key={item.id}>
          <TableCell height={12}>{t(`items:${item.itemKey}`)}</TableCell>
          <TableCell>{item.weight}</TableCell>
        </TableRow>);
      });

      let extraRows = 16;
      let emptyRows = extraRows - result.length < 1 ? 0 : extraRows - result.length
      for(let i = 0; i < emptyRows; i++) {
        result.push(<TableRow key={i} style={{ height: "26px" }}>
          <TableCell sx={{ borderRight: 0, borderColor: "darkgray" }}></TableCell>
          <TableCell sx={{ borderColor: "darkgray" }}></TableCell>
        </TableRow>);
      }

      return result;
    };

    return (
      <>
        <Grid item xs={6}>
          <StyledTable style={{ marginTop: '40px' }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.item")}</TableCell>
                  <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.weight")}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {itemRows()}
              </TableBody>
            </Table>
          </StyledTable>
        </Grid>
        <Grid item xs={6}>
          <StyledTable style={{ marginTop: '40px' }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.item")}</TableCell>
                  <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.weight")}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {itemRows()}
              </TableBody>
            </Table>
          </StyledTable>
        </Grid>
      </>
    );
  }
;