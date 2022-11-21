import { Skill } from "../../types/skill";


interface IProps {
  skills: Skill[] | undefined;
  fetchSkillsHandler: any;
}
export const SkillList = ({ skills, fetchSkillsHandler }: IProps): JSX.Element => {

  const skillItems  = function(): any[] {
    const result: any[] = [];
    skills?.forEach((skill) =>
    {result.push(<div key={skill.id}>{skill.key} - {skill.group} - {skill.baseChance} - {skill.price}</div>)}
    )
    return result;
  }


  return (

    <div>
      {skillItems()}
    </div>
  )
}