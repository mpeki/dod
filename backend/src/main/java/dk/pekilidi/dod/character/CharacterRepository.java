package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.DODCharacter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<DODCharacter, String> {

  List<DODCharacter> findAllByName(String name);

  List<DODCharacter> findAllByOwner(String owner);

  Optional<DODCharacter> findByIdAndOwner(String id, String owner);

  @Override
  List<DODCharacter> findAll();

  void deleteByIdAndOwner(String characterId, String owner);

  int countByOwner(String owner);
}
