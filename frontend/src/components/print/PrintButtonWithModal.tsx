import React, { useState } from 'react';
import { Dialog, IconButton } from '@mui/material';
import PrintIcon from '@mui/icons-material/Print';
import { PrintComponent } from './PrintComponent'; // Make sure to import PrintComponent

interface PrintComponentProps {
  component: React.ReactElement; // this is the component you want to print
}

const PrintButtonWithModal: React.FC<PrintComponentProps> = ({ component }) => {
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div style={{ marginTop: '5px' }}>
      <IconButton size="small" onClick={handleOpen} title="print">
        <PrintIcon style={{ fontSize: '14px' }}/>
      </IconButton>
      <Dialog fullScreen open={open} onClose={handleClose}>
        <PrintComponent component={component} handleClose={handleClose} />
      </Dialog>
    </div>
  );
};

export default PrintButtonWithModal;
