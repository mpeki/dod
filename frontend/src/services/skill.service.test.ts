import { useSkillService } from "./skill.service";

describe( 'Skill Service', () => {
  const { getAllSkills } = useSkillService();
  it('get all skills',async () => {
    await getAllSkills().then((data) => {
      expect(data).toBeTruthy();
      expect(data.length).toBeGreaterThanOrEqual(1);
      expect(data[0].id).toBeTruthy();
    });
  });
});