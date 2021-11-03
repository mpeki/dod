package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.Being;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DODCharacterService {

  @Autowired
  private CharacterRepository characterRepository;

  public DODCharacterService(CharacterRepository characterRepository) {
      this.characterRepository = characterRepository;
  }

  @Cacheable("characters")
  public Being getCharacterByName(String name) {
    Being being = characterRepository.findByName(name);
    if(being == null){
      throw new CharacterNotFoundException();
    }
    return being;
  }
}
