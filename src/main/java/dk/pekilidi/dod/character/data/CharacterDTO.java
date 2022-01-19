package dk.pekilidi.dod.character.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {

    private String name;
    private RaceDTO race;
    private List<BaseTraitDTO> baseTraits;

    public void addBaseTrait(BaseTraitDTO baseTrait){
        if(baseTraits == null){
            baseTraits = new ArrayList<>();
        }
        if(!baseTraits.contains(baseTrait)){
            baseTraits.add(baseTrait);
        }
    }
}
