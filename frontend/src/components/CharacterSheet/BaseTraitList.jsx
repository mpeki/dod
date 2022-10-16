import BaseTraitItem from "./BaseTraitItem";

const BaseTraitList = (props) => {
  const baseTraits = props.baseTraits;
  return (
      Object.keys(baseTraits).map((keyName, i) => (
          <BaseTraitItem key={baseTraits[keyName].id}
                         traitName={baseTraits[keyName].traitName}
                         value={baseTraits[keyName].value}
                         groupValue={baseTraits[keyName].groupValue}/>
      ))
  )
}
export default BaseTraitList
