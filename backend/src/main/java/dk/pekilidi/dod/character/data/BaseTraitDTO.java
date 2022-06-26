package dk.pekilidi.dod.character.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.BaseTraitName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitDTO {

    @JsonIgnore
    @NonNull
    private BaseTraitName traitName;
    private int value = -1;
    private int startValue = -1;
    private int groupValue = -1;
}
