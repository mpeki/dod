package dk.pekilidi.dod.config;

import dk.pekilidi.dod.user.UserRepository;
import dk.pekilidi.dod.user.model.DODUser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserSettingsService {

  private UserRepository userRepository;

  public Optional<Integer> getMaxNpcsForUser(String username) {
    DODUser user = userRepository.findByUsername(username).orElse(null);
    if (user != null && user.getMaxNpcs() != null) {
      return Optional.of(user.getMaxNpcs());
    }
    return Optional.empty();
  }
}
