package dk.pekilidi.dod.character.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaceDTO {

    public RaceDTO(String name) {
        this.name = name;
        this.characterTemplate = new CharacterTemplateDTO();
    }

    @JsonIgnore
    private Long id;
    private String name;
    @JsonIgnore
    private CharacterTemplateDTO characterTemplate;
}
