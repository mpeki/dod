import { BodyPartValue } from "../../types/character";

interface IProps {
  bodyPartName: string;
  bodyPartValue: BodyPartValue | undefined;
}

export const BodyPartItem = ({ bodyPartName, bodyPartValue }: IProps) => {
  return (
    <div>{bodyPartName} - {bodyPartValue?.currentHP} / {bodyPartValue?.maxHP}</div>
  );
};