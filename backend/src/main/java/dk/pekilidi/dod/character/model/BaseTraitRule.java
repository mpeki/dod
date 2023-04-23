package dk.pekilidi.dod.character.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.NonNull;
import lombok.Setter;

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
