package dk.pekilidi.dod.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DiceTest {

    @Test
    void testDie6Rolled3times(){
        int result = Dice.roll("3t6");
        assertTrue(result >= 3 && result <= 18);
    }

    @Test
    void testDie6Rolled3timesPlus3(){
        int result = Dice.roll("3t6+3");
        assertTrue(result >= 3 && result <= 18);
    }

    @Test
    void testDie6Rolled3timesPlus3times1000(){
        for(int i=0; i<1000; i++){
            int result = Dice.roll("3t6+3");
            assertTrue(result >= 6 && result <= 21,  "result should be between 6 and 21, was: " + result);
        }
    }
}