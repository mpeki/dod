package dk.pekilidi.dod.character.model.skill;

import dk.pekilidi.dod.character.model.BaseTraitName;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skill implements Serializable {

  private static final long serialVersionUID = -8970529247843958342L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String key;
  private BaseTraitName traitName;
  private Category category;
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
