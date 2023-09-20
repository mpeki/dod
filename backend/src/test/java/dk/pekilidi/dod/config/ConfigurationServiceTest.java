package dk.pekilidi.dod.config;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.user.UserRepository;
import dk.pekilidi.dod.user.model.DODUser;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ConfigurationServiceTest {

  ConfigurationService configurationService;
  UserRepository userRepo;
  DODUser testUser = new DODUser();
  @BeforeEach
  void setup() {
    userRepo = mock(UserRepository.class);
    configurationService = new ConfigurationService(new UserSettingsService(userRepo));

    testUser.setMaxNpcs(10);
    testUser.setUsername("testUser");

  }

  @Test
  void resolveMaxNpcsForUser() {
    when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(testUser));
    Assertions.assertEquals(10, configurationService.resolveMaxNpcsForUser("testUser"));
  }
}