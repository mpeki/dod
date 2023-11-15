package dk.dodgame.domain.character.model;

import static org.hibernate.annotations.CascadeType.ALL;

import dk.dodgame.domain.character.model.body.BaseBody;
import dk.dodgame.domain.race.model.Race;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class DODCharacter implements Serializable {

  @Serial
  private static final long serialVersionUID = 5434025811976973643L;

  @Id
  @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NonNull
  private String name;

  @NonNull
  private String owner;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "character_trait_mapping",
      joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "base_trait_id", referencedColumnName = "id")})
  @MapKey(name = "traitName")
  @ToString.Exclude
  private Map<BaseTraitName, BaseTrait> baseTraits;

  @NonNull
  @OneToOne(cascade = CascadeType.DETACH)
  private Race race;
  @Column(length = 32, columnDefinition = "varchar(32) default 'MATURE'")
  @Enumerated(EnumType.STRING)
  private AgeGroup ageGroup = AgeGroup.MATURE;
  @Column(length = 32, columnDefinition = "varchar(32) default 'NEW'")
  @Enumerated(EnumType.STRING)
  private CharacterState state = CharacterState.NEW;
  @Column(columnDefinition = "int default -1")
  private int baseSkillPoints = -1;
  @Column(columnDefinition = "int default -1")
  private int heroPoints = -1;

  @NonNull
  @Cascade(ALL)
  @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
  private BaseBody body;

  private boolean hero;

  @Column(columnDefinition = "varchar(32) default 'NA'")
  private String damageBonus = "NA";

  private FavoriteHand favoriteHand;

  private SocialStatus socialStatus;

  @MapKey(name = "skillKey.value")
  @Cascade(ALL)
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinTable(name = "character_skill_mapping",
      joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id")})
  @ToString.Exclude
  private Map<String, CharacterSkill> skills;

  @MapKey(name = "itemName")
  @Cascade(ALL)
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinTable(name = "character_item_mapping",
      joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")})
  @ToString.Exclude
  private Map<String, CharacterItem> items;

  @Embedded
  private Looks looks;

  @Formula("(select sum(ci.quantity * i.weight) from character_item ci "
      + "join character_item_mapping cim on ci.id = cim.item_id " + "join dodcharacter c on cim.character_id = c.id "
      + "join item i on ci.item_key = i.item_key " + "where c.id = id)")
  private Double weightCarried;

  @Embedded
  private Movement movementPoints;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DODCharacter that = (DODCharacter) o;
    return id.equals(that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "DODCharacter{" + "id=" + id + ", name='" + name + '\'' + '}';
  }

  public void addSkill(CharacterSkill skill) {
    skills.putIfAbsent(skill.skillKey.getKeyValue(), skill);
  }
}
