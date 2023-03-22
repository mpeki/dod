package dk.pekilidi.dod.skill;

import com.fasterxml.jackson.annotation.JsonValue;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
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
public class SkillKey implements ChangeKey, Serializable {

  @Serial
  private static final long serialVersionUID = -4561261194340437769L;
  @JsonValue
  private String value;
}
