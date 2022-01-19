package dk.pekilidi.dod.character.data;

import dk.pekilidi.dod.character.BaseTraitName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitDTO {

    @NonNull private BaseTraitName traitName;
    private int value = -1;
    private int startValue = -1;
    private int groupValue = -1;
}
