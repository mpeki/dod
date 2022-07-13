package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.character.AgeGroup;
import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.dod.character.CharacterState;
import dk.pekilidi.dod.character.data.DODFact;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static dk.pekilidi.dod.character.AgeGroup.*;
import static dk.pekilidi.dod.character.CharacterState.NEW;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DODCharacter implements Serializable {

  private static final long serialVersionUID = 5434025811976973643L;

  public DODCharacter(String name, @NonNull Race race) {
    this(null, name, Map.of(), race, MATURE, NEW, -1, -1, false);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "character_trait_mapping",
          joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "base_trait_id", referencedColumnName = "id")})
  @MapKey(name = "traitName")
  @ToString.Exclude
  private Map<BaseTraitName,BaseTrait> baseTraits;

  @NonNull
  @OneToOne(cascade = CascadeType.ALL)
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

  @Transient
  private boolean hero;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DODCharacter that = (DODCharacter) o;
    return id.equals(that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "DODCharacter{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
