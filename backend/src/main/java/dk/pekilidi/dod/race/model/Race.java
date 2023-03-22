package dk.pekilidi.dod.race.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.model.CharacterTemplate;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.With;
import org.hibernate.Hibernate;

@Builder(toBuilder = true)
@With
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "character_template_id"}))
public class Race implements Serializable {

  private static final long serialVersionUID = -8860486223584199305L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(name = "name", unique = true)
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
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
