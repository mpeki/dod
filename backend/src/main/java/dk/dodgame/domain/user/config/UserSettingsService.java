package dk.dodgame.domain.user.config;

import dk.dodgame.domain.user.UserRepository;
import dk.dodgame.domain.user.model.DODUser;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserSettingsService {

  private UserRepository userRepository;

  @Cacheable("user")
  public Optional<Integer> getMaxNpcsForUser(String username) {
    DODUser user = userRepository.findByUsername(username).orElse(null);
    if (user != null && user.getMaxNpcs() != null) {
      return Optional.of(user.getMaxNpcs());
    }
    return Optional.empty();
  }
}
