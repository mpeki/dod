package dk.dodgame.util.rules;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Tag("regression")
class ArmorRulesTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/armor-weight-by-charSize.csv", numLinesToSkip = 1)
    void getArmorWeightForCharSize(int charSize, char armorRef, double expectedWeight) {
        Assertions.assertThat(ArmorRules.getArmorWeightForCharSize(charSize, armorRef)).isEqualTo(expectedWeight);
    }
}