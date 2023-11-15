package dk.dodgame.domain.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class BaseTrait implements Serializable {

  @Serial
  private static final long serialVersionUID = 2277570107538234893L;

  @Id @Tsid
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NonNull
  @Enumerated(EnumType.STRING)
  private BaseTraitName traitName;

  private int currentValue;
  private int startValue;
  private int groupValue;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseTrait baseTrait = (BaseTrait) o;
    return currentValue == baseTrait.currentValue && startValue == baseTrait.startValue
        && groupValue == baseTrait.groupValue && id.equals(baseTrait.id) && traitName.equals(baseTrait.traitName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, traitName, currentValue, startValue, groupValue);
  }

  @Override
  public String toString() {
    return "BaseTrait{" + "id=" + id + ", traitName='" + traitName + '\'' + ", value=" + currentValue + ", startValue="
        + startValue + ", groupValue=" + groupValue + '}';
  }
}
