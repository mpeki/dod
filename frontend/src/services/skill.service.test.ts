import { SkillService } from "./skill.service";

describe( 'Skill Service', () => {
  it('get all skills',async () => {
    await SkillService.getAllSkills().then((data) => {
      expect(data).toBeTruthy();
      expect(data.length).toBeGreaterThanOrEqual(1);
      expect(data[0].id).toBeTruthy();
    });
  });
});