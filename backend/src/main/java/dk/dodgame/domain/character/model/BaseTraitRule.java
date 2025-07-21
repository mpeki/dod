package dk.dodgame.domain.character.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class BaseTraitRule implements Serializable {

  @Serial
  private static final long serialVersionUID = -3987106087156703611L;

  @Id
  @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NonNull
  @Enumerated(EnumType.STRING)
  private BaseTraitName baseTraitName;

  private String baseTraitDieRoll;
  private String baseTraitHeroDieRoll;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseTraitRule that = (BaseTraitRule) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
