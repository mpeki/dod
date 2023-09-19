package dk.pekilidi.dod.user;

import dk.pekilidi.dod.user.model.DODUser;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<DODUser, String> {
  Optional<DODUser> findByUsername(String username);
}
