import ReactToPrint from "react-to-print";
import { IconButton } from "@mui/material";
import PrintIcon from "@mui/icons-material/Print";
import React, { useEffect, useRef, useState } from "react";
import { showPrintInfoSnackbar } from "../../utils/DODSnackbars";
import { PrintButton } from "./PrintButton";

interface PrintComponentProps {
  component: React.ReactElement; // this is the component you want to print
  handleClose: () => void;
}

export const PrintComponent: React.FC<PrintComponentProps> = ({ component, handleClose }) => {
  const componentRef = useRef(null);
  const reactToPrintRef = useRef<ReactToPrint | null>(null);
  const [isPrinting, setIsPrinting] = useState(false);

  useEffect(() => {
    showPrintInfoSnackbar("Before printing, you can edit the character sheet.", handleClose, <PrintButton show={!isPrinting} onPrint={handlePrint}/>);
  }, [handleClose, isPrinting]);

  const handlePrint = () => {
    reactToPrintRef.current?.handlePrint();
  }

  return (
    <div>
      {/* ReactToPrint Trigger */}
      <ReactToPrint
        ref={reactToPrintRef}
        trigger={() => <PrintButton show={false} onPrint={handlePrint} />}
        content={() => componentRef.current}
        onAfterPrint={() => { setIsPrinting(false); handleClose(); }}
        onBeforeGetContent={() => { setIsPrinting(true); return Promise.resolve(); }}
      />
      {/* Wrapping div to encapsulate the component you want to print */}
      <div ref={componentRef}>
        {component}
      </div>
    </div>
  );
};