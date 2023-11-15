package dk.dodgame.domain.user;

import dk.dodgame.domain.user.model.DODUser;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<DODUser, String> {
  Optional<DODUser> findByUsername(String username);
}
