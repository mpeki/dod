import React from "react";

interface IProps {
  onClick: any;
}

export const Button = ({ onClick } : IProps) => {
  return (
    <button onClick={onClick}>Add Character</button>
  );
};