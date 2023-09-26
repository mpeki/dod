import { Character } from "../../types/character";
import { StyledList } from "../shared/List.styled";
import { List, ListItem, ListItemText } from "@mui/material";
import { useTranslation } from "react-i18next";

interface ICharacterProps {
  character: Character;
}

export const MovementStats = ({ character }: ICharacterProps) => {
  const { t } = useTranslation("char");
  return (
    <StyledList>
      <List dense>
        <ListItem>
          <ListItemText primary={t("detail.movementPoints")} secondary="L10 S05" />
        </ListItem>
      </List>
    </StyledList>
  );
}