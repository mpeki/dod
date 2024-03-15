package dk.dodgame.util;

import com.github.javafaker.Faker;
import dk.dodgame.data.CharacterDTO;

public class CharacterTestUtil {
    CharacterDTO createRandomCharacter(){
//        Faker faker = new Faker();
        CharacterDTO characterDTO = CharacterDTO.builder()
//                .name(faker.name().fullName())
//                .age(faker.number().numberBetween(18, 60))
//                .gender(faker.options().option(Gender.class))
                .build();
        return characterDTO;
    }
}
