package dk.pekilidi.dod.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.pekilidi.dod.user.UserRepository;
import dk.pekilidi.dod.user.model.DODUser;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class UserSettingsServiceTest {

  UserRepository userRepo;

  @BeforeEach
  void setup() {
    userRepo = mock(UserRepository.class);
  }

  @Test
  void getMaxNpcsForUserWithUserNoSetting() {
    UserSettingsService userSettingsService = new UserSettingsService(userRepo);
    assertTrue(userSettingsService.getMaxNpcsForUser("testUser").isEmpty());
  }

  @Test
  void getMaxNpcsForNoUser() {
    UserSettingsService userSettingsService = new UserSettingsService(userRepo);
    assertTrue(userSettingsService.getMaxNpcsForUser(null).isEmpty());
  }

  @Test
  void getMaxNpcsForUserWithSetting() {
    Optional<DODUser> userOptional = Optional.of(DODUser.builder().username("testUser").maxNpcs(10).build());
    when(userRepo.findByUsername("testUser")).thenReturn(userOptional);
    UserSettingsService userSettingsService = new UserSettingsService(userRepo);
    assertEquals(userSettingsService.getMaxNpcsForUser("testUser"), Optional.of(10));
  }

  @Test
  void getMaxNpcsForUserWithNoSetting() {
    Optional<DODUser> userOptional = Optional.of(DODUser.builder().username("testUser").maxNpcs(null).build());
    when(userRepo.findByUsername("testUser")).thenReturn(userOptional);
    UserSettingsService userSettingsService = new UserSettingsService(userRepo);
    assertEquals(Optional.empty(), userSettingsService.getMaxNpcsForUser("testUser"));
  }



}