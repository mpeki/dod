import React, { useState, useEffect } from 'react';
import { Fade } from '@mui/material';

type FlashingProps = {
  skipflash?: boolean;
};

function withFlashing<P extends {}>(IconComponent: React.ComponentType<P>) {
  return function FlashingIconComponent(props: P & FlashingProps) {

    const { skipflash, ...restProps } = props;
    const [shown, setShown] = useState(true);

    useEffect(() => {
      if (skipflash) {
        setShown(true);
        return;  // Return early without setting up flashing if skipFlash is true
      }

      const timesToFlash = 4;
      let flashCount = 0;

      const interval = setInterval(() => {
        setShown(prev => !prev);
        flashCount += 1;
        if (flashCount >= timesToFlash) {
          clearInterval(interval);
          setShown(true);
        }
      }, 400);

      return () => clearInterval(interval);
    }, [skipflash]);

    return (
      <Fade in={shown}>
        <IconComponent {...restProps as P} />
      </Fade>
    );
  };
}

export default withFlashing;
