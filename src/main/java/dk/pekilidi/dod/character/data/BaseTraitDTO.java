package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTraitDTO {
    private String traitName;
    private int value = -1;
    private int startValue = -1;
    private int groupValue = -1;
}
