package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeingDTO {

    private String name;
    private RaceDTO race;
}
