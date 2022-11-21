import { Skill } from "../../types/skill";

interface IProps {
  skills: Map<string, Skill> | undefined;
}

export const CharacterSkillList = ({ skills }: IProps): JSX.Element => {

  const charSkillItems = () => {
    const result : JSX.Element[] = []
    if(skills){
      const skillMap: Map<string, Skill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        result.push(<div key={key}>{key} - {skillMap.get(key)?.fv} - {skillMap.get(key)?.experience}</div>);
      });
    }
    return result;
  }


  return (
        <div> {charSkillItems()} </div>
  )

}