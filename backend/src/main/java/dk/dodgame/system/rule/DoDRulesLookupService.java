package dk.dodgame.system.rule;

import dk.dodgame.data.ItemDTO;
import dk.dodgame.util.rules.ArmorRules;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class DoDRulesLookupService {

    public Double lookupArmorWeightForSize(int characterSize, char weightRef) {
        return ArmorRules.getArmorWeightForCharSize(characterSize, weightRef);
    }

    public Double calculatePriceForSize(int characterSize, ItemDTO item) {
        return ArmorRules.getArmorPriceForSize(characterSize, item);
    }

}