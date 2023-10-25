import { BaseTraitValue } from "../../types/character";
import { BaseTraitItem } from "./BaseTraitItem";
import { Table, TableBody, TableHead, TableRow, TableCell } from "@mui/material";
import { StyledTable } from "../shared/Table.styled";
import { useTranslation } from "react-i18next";

interface IProps {
  baseTraits: Record<string, BaseTraitValue> | undefined;
}

export const BaseTraitList = ({ baseTraits }: IProps): JSX.Element => {
  const {t} = useTranslation("char");

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
        <StyledTable style={{ margin: '2px', marginTop: '15px' }} >
          <Table>
            <TableHead>
              <TableRow>
                <TableCell colSpan={2}></TableCell>
                <TableCell align="right" colSpan={2} sx={{ fontWeight: 'bold' }}>{t("detail.baseTraits.value")}</TableCell>
                <TableCell align="right" colSpan={2} sx={{ fontWeight: 'bold' }}>{t("detail.baseTraits.group")}</TableCell>
                <TableCell colSpan={3}></TableCell>
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