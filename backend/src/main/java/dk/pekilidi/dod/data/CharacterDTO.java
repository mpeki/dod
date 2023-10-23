package dk.pekilidi.dod.data;

import static dk.pekilidi.dod.character.model.AgeGroup.MATURE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.FavoriteHand;
import dk.pekilidi.dod.character.model.Looks;
import dk.pekilidi.dod.character.model.Movement;
import dk.pekilidi.dod.character.model.SocialStatus;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import dk.pekilidi.dod.items.InsufficientFundsException;
import dk.pekilidi.dod.items.model.Coin;
import dk.pekilidi.dod.items.model.ItemType;
import dk.pekilidi.dod.items.model.ManyPiece;
import dk.pekilidi.dod.util.rules.RulesUtil;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CharacterDTO implements DODFact, Serializable {

  @Serial
  private static final long serialVersionUID = -4787939824473814563L;

  private String id;
  @Default
  private String name = "NONAME";
  private RaceDTO race;
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<BaseTraitName, BaseTraitDTO> baseTraits = new EnumMap<>(BaseTraitName.class);
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<BodyPartName, BodyPartDTO> bodyParts = new EnumMap<>(BodyPartName.class);
  @Default
  private AgeGroup ageGroup = MATURE;
  private CharacterState state;
  private int baseSkillPoints;
  @Default
  private int heroPoints = -1;
  @Default
  private boolean hero = false;
  @Default
  private String damageBonus = "NA";
  private FavoriteHand favoriteHand;
  private SocialStatus socialStatus;
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<String, CharacterSkillDTO> skills = new HashMap<>();
  @Default
  @EqualsAndHashCode.Exclude
  @JsonInclude(Include.NON_EMPTY)
  private Map<String, CharacterItemDTO> items = new HashMap<>();

  private Looks looks;
  private Double weightCarried;
  private Movement movementPoints;

  public Integer getBaseTraitValue(BaseTraitName baseTraitName) {
    if (baseTraits.containsKey(baseTraitName)) {
      return baseTraits.get(baseTraitName).getCurrentValue();
    }
    return -1;
  }

  public Integer getBaseTraitGroupValue(BaseTraitName baseTraitName) {
    if (baseTraits.containsKey(baseTraitName)) {
      return baseTraits.get(baseTraitName).getGroupValue();
    }
    return -1;
  }

  public void addSkill(@NotNull CharacterSkillDTO skillDTO) {
    skills.putIfAbsent(skillDTO.getSkill().getKey().getKeyValue(), skillDTO);
  }

  public void updateFv(@NotNull String skillKey, @NotNull int newValue) {
    CharacterSkillDTO skillDTO = skills.get(skillKey);
    skillDTO.setFv(newValue);
    skills.put(skillKey, skillDTO);
  }

  public CharacterSkillDTO getSkill(String skillKey) {
    return skills.get(skillKey);
  }

  public void addBaseTrait(BaseTraitDTO baseTrait) {
    baseTraits.putIfAbsent(baseTrait.getTraitName(), baseTrait);
  }

  public void addBodyPart(BodyPartDTO bodyPart) {
    bodyParts.putIfAbsent(bodyPart.getName(), bodyPart);
  }

  public void incrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      int newValue = v.getCurrentValue() + by;
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }

  public void decrementTrait(BaseTraitName traitName, int by) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      int newValue = v.getCurrentValue() - by;
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }

  public void updateBaseTrait(BaseTraitName traitName, int newValue) {
    baseTraits.computeIfPresent(traitName, (k, v) -> {
      v.setCurrentValue(newValue);
      v.setGroupValue(RulesUtil.calculateGroupValue(newValue));
      return v;
    });
  }

  @JsonIgnore
  public int getAmountOf(Coin coinType) {
    CharacterItemDTO coin = items.get(coinType.keyOf());
    return coin != null ? coin.getQuantity() : 0;
  }

  public int addCoins(int amount, Coin coinType) {
    CharacterItemDTO coinItem = items.get(coinType.keyOf());
    if (coinItem != null) {
      coinItem.setQuantity(coinItem.getQuantity() + amount);
    } else {
      coinItem = addManyPieceItem(amount, coinType);
    }
    return coinItem.getQuantity();
  }

  public int subtractCoins(int amount, Coin coinType) throws InsufficientFundsException {
    CharacterItemDTO coinItem = items.get(coinType.keyOf());
    if (coinItem != null) {
      if (coinItem.getQuantity() < amount) {
        throw new InsufficientFundsException(
            "Not enough coins, you have " + coinItem.getQuantity() + " " + coinType + " coins. ");
      } else {
        coinItem.setQuantity(coinItem.getQuantity() - amount);
      }
    } else {
      throw new InsufficientFundsException("Not enough coins, you have 0 " + coinType + " coins. ");
    }
    return coinItem.getQuantity();
  }

  public CharacterItemDTO addManyPieceItem(int quantity, ManyPiece type) {
    String key = type.keyOf();
    CharacterItemDTO item = items.get(key);
    if (item != null) {
      item.setQuantity(item.getQuantity() + quantity);
    } else {
      item = CharacterItemDTO
          .builder()
          .itemName(key)
          .item(ItemDTO.builder().itemType(type.getItemType()).build())
          .quantity(quantity)
          .build();
    }
    items.put(key, item);
    return items.get(key);
  }

  public void addItem(@NotNull CharacterItemDTO item) {
    if (item.getItemName() == null) {
      String itemKey = item.getItem().getItemKey().getKeyValue();
      Set<String> set = this
          .getItems()
          .keySet()
          .stream()
          .filter(s -> s.startsWith(itemKey))
          .collect(Collectors.toSet());
      item.setItemName(itemKey + '_' + set.size());
    }
    if (items.containsKey(item.getItemName())) {
      CharacterItemDTO existingItem = items.get(item.getItemName());
      if (existingItem.getItem().getItemType() == ItemType.PROJECTILE
          && item.getItem().getItemType() == ItemType.COIN) {
        existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
      }
      return;
    }
    items.putIfAbsent(item.getItemName(), item);
  }

  public int getNumberOf(ManyPiece type) {
    if (type == null || !items.containsKey(type.keyOf())) {
      return 0;
    }
    return items.get(type.keyOf()).getQuantity();
  }

  public void removeSkill(String skillKey) {
    skills.remove(skillKey);
  }

}
