import { Avatar, Card, CardActions, CardContent, CardHeader, Grid, Skeleton } from "@mui/material";
import React, { ReactNode } from "react";

interface EmptyCharacterCardProps {
  children?: ReactNode;
}

const styles = {
  card: {
    minWidth: 200,
    minHeight: 220,
    position: "relative" as "relative",
    backgroundColor: 'rgba(255, 255, 255, 0.4)',
  },
  cardActions: {
    position: "absolute" as "absolute",
    bottom: 0,
    left: 0,
    padding: "8px",
    opacity: 1
  }
};


export const EmptyCharacterCard: React.FC<EmptyCharacterCardProps> = ({ children }) => {
  return (
    <Grid item xs={2} sm={3} md={3} padding={.2}>
      <Card
        elevation={5}
        variant="elevation"
        style={styles.card}
      >
        <CardHeader
          avatar={
            <Avatar aria-label="recipe">?</Avatar>
          }
          title=""
          subheader=""
        />
        <CardContent>
          <Skeleton animation={false} variant="text" />
          <Skeleton animation={false} variant="text" />
          <Skeleton animation={false} variant="text" />
        </CardContent>
        <CardActions style={styles.cardActions}>
          {children}
        </CardActions>
      </Card>
    </Grid>
  );
};