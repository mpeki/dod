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
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SkillService {

  private static final ModelMapper modelMapper = new ModelMapper();
  private SkillRepository skillRepository;

  public static Integer calculateNewSkillPrice(CharacterDTO characterDTO, SkillDTO skill, Integer fvToBuy) {
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
    return calculateCatBSkillCost(skill, pointsToBuy, 0);
  }

  private static int calculateCatBSkillCost(SkillDTO skill, int pointsToBuy, int currentFv) {
    pointsToBuy = currentFv + pointsToBuy;
    int result;
    int skillPrice = skill.getPrice();
    int tier1Price = skillPrice * 2;
    int tier2Price = tier1Price + (skillPrice * 4);
    switch (pointsToBuy) {
      case 1, 2 -> result = skillPrice * pointsToBuy;
      case 3, 4 -> result = tier1Price + ((pointsToBuy - 2) * skillPrice * 2);
      case 5 -> result = tier2Price + ((pointsToBuy - 4) * skillPrice * 3);
      default -> throw new IllegalStateException("WTF!");
    }
    return result;
  }

  private static int calculateCatASkillCost(SkillDTO skill, int pointsToBuy) {
    int result;
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

  public static int calculatePriceForFVIncrease(CharacterDTO characterDTO, String skillKey, int fvToBuy) {
    SkillDTO skill = characterDTO.getSkills().get(skillKey);
    if (skill.getCategory() == Category.B) {
      return calculatePriceForFVIncreaseCatB(characterDTO, skill, fvToBuy);
    }
    int currentFv = characterDTO.getSkills().get(skillKey).getFv();
    int newFV = currentFv + fvToBuy;
    int cost = 0;
    for (int i = currentFv + 1; i < newFV + 1; i++) {
      switch (i) {
        case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> cost += skill.getPrice();
        case 11, 12, 13, 14 -> cost += skill.getPrice() * 2 + 1;
        case 15, 16, 17 -> cost += skill.getPrice() * 3 + 2;
        case 18, 19, 20 -> cost += skill.getPrice() * 4 + 3;
        case 21, 22, 23 -> cost += skill.getPrice() * 5 + 4;
        case 24, 25, 26 -> cost += skill.getPrice() * 6 + 5;
        case 27, 28, 29 -> cost += skill.getPrice() * 7 + 6;
        case 30, 31, 32 -> cost += skill.getPrice() * 8 + 7;
        case 33, 34, 35 -> cost += skill.getPrice() * 9 + 8;
        case 36, 37, 38 -> cost += skill.getPrice() * 10 + 9;
        case 39, 40, 41 -> cost += skill.getPrice() * 11 + 10;
        case 42, 43, 44 -> cost += skill.getPrice() * 12 + 11;
        case 45, 46, 47 -> cost += skill.getPrice() * 13 + 12;
        case 48, 49, 50 -> cost += skill.getPrice() * 14 + 13;
        case 51, 52, 53 -> cost += skill.getPrice() * 15 + 14;
        case 54, 55, 56 -> cost += skill.getPrice() * 16 + 15;
        case 57, 58, 59 -> cost += skill.getPrice() * 17 + 16;
        case 60, 61, 62 -> cost += skill.getPrice() * 18 + 17;
        case 63, 64, 65 -> cost += skill.getPrice() * 19 + 18;
        case 66, 67, 68 -> cost += skill.getPrice() * 20 + 19;
        case 69, 70, 71 -> cost += skill.getPrice() * 21 + 20;
        case 72, 73, 74 -> cost += skill.getPrice() * 22 + 21;
        case 75, 76, 77 -> cost += skill.getPrice() * 23 + 22;
        case 78, 79, 80 -> cost += skill.getPrice() * 24 + 23;

        default -> throw new IllegalStateException("Max FV is 80");
      }
    }
    return cost;
  }

  private static int calculatePriceForFVIncreaseCatB(CharacterDTO characterDTO, SkillDTO skill, int fvToBuy) {
    return 0;
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
      throw new SkillNotFoundException("Skill for key: " + key.getKeyValue() + " not found!");
    }
    return modelMapper.map(skill, SkillDTO.class);
  }
}
