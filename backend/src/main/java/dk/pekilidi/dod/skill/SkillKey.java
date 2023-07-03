package dk.pekilidi.dod.skill;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NONE;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import dk.pekilidi.dod.actions.model.ActionKey;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import dk.pekilidi.dod.items.ItemKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class SkillKey implements ChangeKey, ActionKey, Serializable {

  @Serial
  private static final long serialVersionUID = -4561261194340437769L;

  @JsonValue
  @Column(name = "skill_key")
  private String value;
  // https://hibernate.atlassian.net/browse/HHH-15307

  public SkillKey getValue() {
    return this;
  }

  public String getKeyValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SkillKey skillKey)) {
      return false;
    }
    return Objects.equals(value, skillKey.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static SkillKey toSkillKey(String key){
    return SkillKey.builder().value(key).build();
  }
}
