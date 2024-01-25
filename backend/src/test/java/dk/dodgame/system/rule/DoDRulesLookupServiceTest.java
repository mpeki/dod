package dk.dodgame.system.rule;

import dk.dodgame.util.rules.ArmorRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Tag("regression")
class DoDRulesLookupServiceTest {

    DoDRulesLookupService cut;

    @BeforeEach
    void setUp() {
        cut = new DoDRulesLookupService();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/sizeAndArmorRef.csv", numLinesToSkip = 1)
    void lookupArmorWeightForSize(int charSize, char armorRef, double result) {
        Assertions.assertEquals(result, ArmorRules.getArmorWeightForCharSize(charSize, armorRef), "size: " + charSize + ", armorRef: " + armorRef); //cut.lookupArmorWeightForSize(charSize,armorRef)
    }

}