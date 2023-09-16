import React, { useState, useEffect, ComponentType } from 'react';
import { Fade } from '@mui/material';

type FlashingProps = {
  skipFlash?: boolean;
};

function withFlashing<T extends {}>(IconComponent: ComponentType<T>) {
  return function FlashingIconComponent(props: T & FlashingProps) {

    const { skipFlash, ...restProps } = props;
    const [shown, setShown] = useState(true);

    useEffect(() => {
      if (skipFlash) {
        setShown(true);
        return;  // Return early without setting up flashing if skipFlash is true
      }

      const timesToFlash = 3;
      let flashCount = 0;

      const interval = setInterval(() => {
        setShown(prev => !prev);
        flashCount += 0.5;
        if (flashCount >= timesToFlash) {
          clearInterval(interval);
          setShown(true);
        }
      }, 500);

      return () => clearInterval(interval);
    }, []);

    return (
      <Fade in={shown}>
        <IconComponent {...props} />
      </Fade>
    );
  };
}

export default withFlashing;
