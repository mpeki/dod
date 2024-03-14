import React from "react";
import { Box, LinearProgress, Tooltip, Typography } from "@mui/material";

interface IProps {
  label: string;
  maxValue: number;
  currentValue: number;
  yellowThreshold?: number;
  redThreshold?: number;
  goodDirection?: "full" | "empty";
}

export const StatusLinearProgress = ({ label, maxValue, currentValue, yellowThreshold = 6, redThreshold = 3, goodDirection = "full" }: IProps) => {
  const getStatusColor = (currentValue: number) => {
    if(goodDirection === "empty") {
      if (currentValue < yellowThreshold) return "success";
      if (currentValue >= redThreshold) return "error";
      if (currentValue >= yellowThreshold) return "warning";
    } else {
      if (currentValue > yellowThreshold) return "success";
      if (currentValue <= yellowThreshold) return "warning";
      if (currentValue <= redThreshold) return "error";
    }
  };

  const normalise = (currentValue: number, maxValue: number) => {
    return currentValue <= maxValue ? ((currentValue / maxValue) * 100) : 100;
  };
  const normalisedValue = normalise(currentValue, maxValue);

  const createTooltip = (currentValue: number, maxValue: number) => {
    return `${currentValue} / ${maxValue}`
  }

  return (
    <Tooltip title={createTooltip(currentValue, maxValue)}>
      <Box>
        <Typography variant="body2" width={180} paddingLeft={1} style={{ fontSize: 12 }}>
          {label}
        </Typography>
        <LinearProgress variant="determinate" value={normalisedValue} color={getStatusColor(currentValue)} />
      </Box>
    </Tooltip>

  );
};


