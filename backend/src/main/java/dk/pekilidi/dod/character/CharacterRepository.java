package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.DODCharacter;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<DODCharacter,Long> {
  List<DODCharacter> findByName(String name);



//  DODCharacter findById(BigDecimal id);
}
