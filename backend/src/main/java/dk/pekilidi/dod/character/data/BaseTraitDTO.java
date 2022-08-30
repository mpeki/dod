package dk.pekilidi.dod.character.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.BaseTraitName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitDTO implements Serializable {

    private static final long serialVersionUID = -2330840748162459050L;

    public BaseTraitDTO(@NonNull BaseTraitName traitName, int value, int startValue, int groupValue) {
        this.traitName = traitName;
        this.value = value;
        this.startValue = startValue;
        this.groupValue = groupValue;
    }

    @JsonIgnore
    private Long id;
    @JsonIgnore
    @NonNull
    private BaseTraitName traitName;
    private int value = -1;
    private int startValue = -1;
    private int groupValue = -1;
}
