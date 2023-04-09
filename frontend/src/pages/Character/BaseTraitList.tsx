import { BaseTraitValue } from "../../types/character";
import { BaseTraitItem } from "./BaseTraitItem";

interface IProps {
  baseTraits: Map<string, BaseTraitValue> | undefined;
}

export const BaseTraitList = ({ baseTraits }: IProps): JSX.Element => {

  if (baseTraits) {
    const baseTraitMap: Map<string, BaseTraitValue> = new Map(Object.entries(baseTraits));
    const baseTraitItems  = function(): any[] {
      const items = []
      for(let [key,value] of baseTraitMap ){
        items.push(<BaseTraitItem key={key} baseTraitName={key} itemValues={value} />)
      }
      return items;
    }

    return (
      <>
        <h3>Base Traits:</h3>
        {baseTraitItems()}
      </>
    );
  } else return (<></>);
};