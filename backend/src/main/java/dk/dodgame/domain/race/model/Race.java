package dk.dodgame.domain.race.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

import dk.dodgame.domain.character.model.CharacterTemplate;
import dk.dodgame.domain.skill.SkillKey;

@Builder(toBuilder = true)
@With
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "character_template_id"}))
public class Race implements Serializable {

  @Serial
  private static final long serialVersionUID = -8860486223584199305L;

  @Id @Tsid
  private String id;

  @NonNull
  @Column(name = "name", unique = true)
  private String name;

  @AttributeOverride(name="value", column=@Column(name="mother_tongue"))
  @Embedded
  private SkillKey motherTongue;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "character_template_id", referencedColumnName = "id")
  @JsonIgnore
  private CharacterTemplate characterTemplate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Race that) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
