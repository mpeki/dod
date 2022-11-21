package dk.pekilidi.dod.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.model.Category;
import dk.pekilidi.dod.skill.model.Group;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SkillService {

  private static final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private SkillRepository skillRepository;

  public static Integer calculateNewSkillPrice(CharacterDTO characterDTO, SkillDTO skill, Integer fvToBuy) {
    int result = -1;
    Integer freePoints = 0;
    if (skill.getCategory() == Category.A && characterDTO.isHero()) {
      freePoints = characterDTO.getBaseTraits().get(skill.getTraitName()).getGroupValue();
    }
    int pointsToBuy = fvToBuy - freePoints;
    if (pointsToBuy < 1) {
      return 0;
    } else if (skill.getCategory() == Category.B && fvToBuy > 5) {
      throw new IllegalArgumentException("Category B skills can't start above 5 fv");
    } else if (skill.getCategory() == Category.A && fvToBuy > 20) {
      throw new IllegalArgumentException("Category A skills can't start above 20 fv");
    }
    if (skill.getCategory() == Category.A) {
      return calculateCatASkillCost(skill, pointsToBuy);
    } else {
      return calculateCatBSkillCost(skill, pointsToBuy);
    }
  }

  private static int calculateCatBSkillCost(SkillDTO skill, int pointsToBuy) {
    int result = -1;
    int skillPrice = skill.getPrice();
    int tier1Price = skillPrice * 2;
    int tier2Price = tier1Price + (skillPrice * 4);
    int tier3Price = tier2Price + (skillPrice * 3);
    switch (pointsToBuy) {
      case 1, 2 -> result = skillPrice * pointsToBuy;
      case 3, 4 -> result = tier1Price + ((pointsToBuy - 2) * skillPrice * 2);
      case 5 -> result = tier2Price + ((pointsToBuy - 4) * skillPrice * 3);
      default -> throw new IllegalStateException("WTF!");
    }
    return result;
  }

  private static int calculateCatASkillCost(SkillDTO skill, int pointsToBuy) {
    int result = -1;
    int skillPrice = skill.getPrice();
    int tier1Price = skillPrice * 10;
    int tier2Price = tier1Price + (skillPrice * 8);
    int tier3Price = tier2Price + (skillPrice * 9);
    switch (pointsToBuy) {
      case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> result = skillPrice * pointsToBuy;
      case 11, 12, 13, 14 -> result = tier1Price + ((pointsToBuy - 10) * skillPrice * 2);
      case 15, 16, 17 -> result = tier2Price + ((pointsToBuy - 14) * skillPrice * 3);
      case 18, 19, 20 -> result = tier3Price + ((pointsToBuy - 17) * skillPrice * 4);
      default -> throw new IllegalStateException("WTF!");
    }
    return result;
  }

  public static List<Integer> getNewSkillPriceRange(CharacterDTO characterDTO, SkillDTO skill) {
    int cap = skill.getCategory() == Category.A ? 20 : 5;
    List<Integer> result = new ArrayList<>(cap);
    for (int i = 0; i < cap + 1; i++) {
      result.add(calculateNewSkillPrice(characterDTO, skill, i));
    }
    return result;
  }

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

  @Cacheable("skills")
  public SkillDTO findSkillByKey(String skillKey) {
    return findSkillByKey(SkillKey.builder().value(skillKey).build());
  }

  @Cacheable("skills")
  public SkillDTO findSkillByKey(SkillKey key) {
    Skill skill = skillRepository.findByKey(key);
    if (skill == null) {
      throw new SkillNotFoundException("Skill for key: " + key.getValue() + " not found!");
    }
    return modelMapper.map(skill, SkillDTO.class);
  }
}
