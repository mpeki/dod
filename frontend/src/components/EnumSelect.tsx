import { Group } from "../types/group";
import { BaseChance } from "../types/basechance";

interface IProps {
  onChange : any;
  selectType : typeof Group | typeof BaseChance
}

export const EnumSelect = ({ selectType, onChange } : IProps ) : JSX.Element => {
  const renderOption = (text: string) => {
    return <option key={text} value={text.toUpperCase()}>{text}</option>
  }
  const options = Object.values(selectType).map(
    (value: string) => {
      return value;
    })

  return (
    <select onChange={onChange}>{options.map(renderOption)}</select>
  )
}


// export const SkillGroupSelect = ({ onChange, selectType } : IProps ) : JSX.Element => {
//   const renderOption = (text: string) => {
//     return <option key={text} value={text.toUpperCase()}>{text}</option>
//   }
//
//   const options = Object.values(selectType).map(
//     (value: string) => {
//       return value;
//     })
//
//   return (
//     <select onChange={onChange}>{options.map(renderOption)}</select>
//   )
// }