import { Skill } from "../../types/skill";
import { CharacterSkill } from "./CharacterSkill";

interface IProps {
  charId: string;
  skills: Record<string, Skill>;
}

export const CharacterSkillList = ({ charId, skills }: IProps): JSX.Element => {

  const charSkillItems = () => {
    const result : JSX.Element[] = []
    if(skills){
      const skillMap: Map<string, Skill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        if(value != null){
          let skill = skillMap.get(key)
          if(skill != null){
            result.push(<CharacterSkill key={skill.key} characterId={charId} skill={skill} />);
          }
        }
        // result.push(<div key={key}>{key} - {skillMap.get(key)?.fv}</div>);
      });
    }
    return result;
  }


  return (
        <div> {charSkillItems()} </div>
  )

}