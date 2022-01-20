package dk.pekilidi.dod.character.data;

import dk.pekilidi.dod.character.AgeGroup;
import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.dod.character.CharacterState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {

    private String name;
    private RaceDTO race;
    private Map<BaseTraitName,BaseTraitDTO> baseTraits;
    private AgeGroup ageGroup;
    private CharacterState state;
    private int baseSkillPoints;

    public CharacterDTO(String name, RaceDTO race, Map<BaseTraitName,BaseTraitDTO> baseTraits) {
        this(name,race,baseTraits,AgeGroup.MATURE, CharacterState.NEW, -1);
    }

    public void addBaseTrait(BaseTraitDTO baseTrait){
        if(baseTraits == null){
            baseTraits = new EnumMap<>(BaseTraitName.class);
        }
        baseTraits.putIfAbsent(baseTrait.getTraitName(), baseTrait);
    }

    public void incrementTrait(BaseTraitName traitName, int by){

        if(baseTraits != null){
            baseTraits.computeIfPresent(traitName,  (k, v) -> {
                v.setValue(v.getValue() + by); return v;
            });
        }
    }

    public void decrementTrait(BaseTraitName traitName, int by){
        if(baseTraits != null){
            baseTraits.computeIfPresent(traitName,  (k, v) -> {
                v.setValue(v.getValue() - by); return v;
            });
        }
    }

    public void updateBaseTrait(BaseTraitName traitName, int newValue){
        if(baseTraits != null){
            baseTraits.computeIfPresent(traitName,  (k, v) -> {
                v.setValue(newValue); return v;
            });
        }
    }

}
