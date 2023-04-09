package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.DODCharacter;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<DODCharacter, String> {

  List<DODCharacter> findAllByName(String name);

  @Override
  List<DODCharacter> findAll();
}
