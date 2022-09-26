package dk.pekilidi.dod.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.model.Group;
import dk.pekilidi.dod.skill.model.Skill;
import dk.pekilidi.dod.util.repo.OptionalCheck;
import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

  private static final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private SkillRepository skillRepository;

  @Cacheable("skills")
  public List<SkillDTO> getSkillsByGroup(Group group) {
    List<Skill> entities = skillRepository.findByGroup(group);
    return modelMapper.map(entities, new TypeToken<List<SkillDTO>>() {}.getType());
  }

  @Cacheable("skills")
  public List<SkillDTO> getSkills() {
    List<Skill> entities = IterableUtils.toList(skillRepository.findAll());
    return modelMapper.map(entities, new TypeToken<List<SkillDTO>>() {}.getType());
  }

  @Cacheable("skills")
  public List<SkillDTO> getSkillsByBaseChance(BaseTraitName baseChance) {
    List<Skill> entities = skillRepository.findByBaseChance(baseChance);
    return modelMapper.map(entities, new TypeToken<List<SkillDTO>>() {}.getType());
  }

  @Cacheable("skills")
  public List<SkillDTO> getSkillsByGroupAndBaseChance(Group group, BaseTraitName baseChance) {
    List<Skill> entities = skillRepository.findByGroupAndBaseChance(group, baseChance);
    return modelMapper.map(entities, new TypeToken<List<SkillDTO>>() {}.getType());
  }

}
