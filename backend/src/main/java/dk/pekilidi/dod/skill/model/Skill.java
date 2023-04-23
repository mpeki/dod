package dk.pekilidi.dod.skill.model;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.skill.SkillKey;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Skill that = (Skill) o;
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
