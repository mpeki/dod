package dk.dodgame.domain.skill.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import org.hibernate.Hibernate;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.domain.skill.SkillKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Skill implements Serializable {

  @Serial
  private static final long serialVersionUID = -8970529247843958342L;

  @Id
  @Tsid
  private String id;
  @Embedded
  @AttributeOverride(name = "key", column = @Column(name = "skill_key"))
  private SkillKey key;
  private BaseTraitName traitName;
  private Category category;
  @Column(name = "skill_group")
  private Group group;
  private Integer price;
  private BaseTraitName baseChance;
  @OneToMany
  private List<Race> deniedRaces;
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Skill that) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key);
  }

  @Override
  public String toString() {
    return "Skill{" + "id=" + id + ", name='" + key + '\'' + '}';
  }
}
