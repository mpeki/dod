package dk.pekilidi.dod.character.model;

import static dk.pekilidi.dod.character.model.AgeGroup.MATURE;
import static dk.pekilidi.dod.character.model.CharacterState.NEW;

import dk.pekilidi.dod.character.model.body.BaseBody;
import dk.pekilidi.dod.race.model.Race;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DODCharacter implements Serializable {

  private static final long serialVersionUID = 5434025811976973643L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String name;

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
  private AgeGroup ageGroup = MATURE;

  @Column(length = 32, columnDefinition = "varchar(32) default 'NEW'")
  @Enumerated(EnumType.STRING)
  private CharacterState state = NEW;

  @Column(columnDefinition = "int default -1")
  private int baseSkillPoints = -1;

  @Column(columnDefinition = "int default -1")
  private int heroPoints = -1;

  @NonNull
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private BaseBody body;

  private boolean hero;

  @Column(columnDefinition = "varchar(32) default 'NA'")
  private String damageBonus = "NA";

  private FavoriteHand favoriteHand;
  private SocialStatus socialStatus;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "character_skill_mapping",
      joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id")})
  @ToString.Exclude
  private Map<String, CharacterSkill> skills;

  @Embedded
  private Looks looks;

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
}
