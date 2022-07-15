package dk.pekilidi.dod.character.data;

import static dk.pekilidi.dod.character.AgeGroup.*;

import dk.pekilidi.dod.character.AgeGroup;
import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.dod.character.CharacterState;
import dk.pekilidi.dod.rules.changes.ChangeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO implements DODFact {

    private Long id;
    private String name;
    private RaceDTO race;
    private Map<BaseTraitName,BaseTraitDTO> baseTraits;
    @Builder.Default
    private AgeGroup ageGroup = MATURE;
    private CharacterState state;
    private int baseSkillPoints;
    @Builder.Default
    private int heroPoints = -1;
    @Builder.Default
    private boolean hero = false;

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
