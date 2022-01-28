package dk.pekilidi.dod.character.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceDTO {
    private String name;
    @JsonIgnore
    private CharacterTemplateDTO characterTemplate;
}
