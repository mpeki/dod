import { BodyPartValue } from "../../types/character";
import { BodyPartItem } from "./BodyPart";

interface IProps {
  parts: Map<string, BodyPartValue> | undefined;
}

export const BodyContainer = ({ parts }: IProps): JSX.Element => {

  if (parts) {
    const bodyPartMap: Map<string, BodyPartValue> = new Map(Object.entries(parts));
    const bodyParts  = function(): any[] {
      const items = []
      for(let [key,value] of bodyPartMap ){
        items.push(<BodyPartItem key={key} bodyPartName={key} bodyPartValue={value} />)
      }
      return items;
    }

    return (
      <>
        <h3>Body:</h3>
        {bodyParts()}
      </>
    );
  } else return (<></>);
};