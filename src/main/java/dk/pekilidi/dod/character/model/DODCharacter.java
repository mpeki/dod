package dk.pekilidi.dod.character.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

  private String name;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name= "dodcharacter_id")
  @ToString.Exclude
  private List<BaseTrait> baseTraits;

  @NonNull
  @OneToOne
  private Race race;

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
