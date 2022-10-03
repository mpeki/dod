package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.model.BaseTraitName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseTraitDTO implements Serializable {

  private static final long serialVersionUID = -2330840748162459050L;

  public BaseTraitDTO(@NonNull BaseTraitName traitName, int currentValue, int startValue, int groupValue) {
    this.traitName = traitName;
    this.currentValue = currentValue;
    this.startValue = startValue;
    this.groupValue = groupValue;
  }

  @JsonIgnore
  private Long id;
  @JsonIgnore
  @NonNull
  private BaseTraitName traitName;
  @Builder.Default
  private int currentValue = -1;
  @Builder.Default
  private int startValue = -1;
  @Builder.Default
  private int groupValue = -1;
}
