import { BaseTraitValue } from "../../types/character";

interface IProps {
  baseTraitName: string;
  itemValues: BaseTraitValue | undefined;
}

export const BaseTraitItem = ({ baseTraitName, itemValues }: IProps) => {
  return (
    <div title={"Start value:" + itemValues?.startValue}>{baseTraitName} - {itemValues?.currentValue} : ({itemValues?.groupValue})</div>
  );
};