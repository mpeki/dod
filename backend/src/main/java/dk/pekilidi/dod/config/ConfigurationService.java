package dk.pekilidi.dod.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

  private final UserSettingsService userSettingsService;

  public ConfigurationService(UserSettingsService userSettingsService) {
    this.userSettingsService = userSettingsService;
  }

  @Value("${dodgame.player.max-characters}")
  private int defaultMaxNpcs;

  public int resolveMaxNpcsForUser(String username) {
    return userSettingsService.getMaxNpcsForUser(username)
        .orElse(defaultMaxNpcs);
  }
}
