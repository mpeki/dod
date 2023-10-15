import { GroupType } from "../types/group";
import { BaseChance } from "../types/basechance";
import { Category } from "../types/category";

interface IProps {
  onChange: any;
  selectType: typeof GroupType | typeof BaseChance | typeof Category;
}

export const EnumSelect = ({ selectType, onChange }: IProps): JSX.Element => {
  const renderOption = (text: string) => {
    return <option key={text} value={text.toUpperCase()}>{text}</option>;
  };
  const options = Object.values(selectType).map(
    (value: string) => {
      return value;
    });

  return (
    <select onChange={onChange}>{options.map(renderOption)}</select>
  );
};