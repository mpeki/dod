package dk.pekilidi.dod.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.Hibernate;

@Builder(toBuilder = true)
@With
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "character_template_id"})
)
public class Race implements Serializable {

  private static final long serialVersionUID = -8860486223584199305L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(name="name", unique=true)
  private String name;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "character_template_id", referencedColumnName = "id")
  @JsonIgnore
  private CharacterTemplate characterTemplate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Race that = (Race) o;
    return getId() != null && Objects.equals(getId(),that.getId());
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
