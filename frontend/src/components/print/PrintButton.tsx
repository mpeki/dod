import { IconButton } from "@mui/material";
import PrintIcon from "@mui/icons-material/Print";

interface PrintButtonProps {
  show: boolean;
  onPrint: () => void;
}

export const PrintButton: React.FC<PrintButtonProps> = ({ show, onPrint }) => {
  if (!show) return null;

  return (
    <IconButton size="small" onClick={onPrint}>
      <PrintIcon style={{ fontSize: '16px' }} />
    </IconButton>
  );
};