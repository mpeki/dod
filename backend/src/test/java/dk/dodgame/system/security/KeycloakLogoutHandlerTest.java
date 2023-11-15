package dk.dodgame.system.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import dk.dodgame.system.security.KeycloakLogoutHandler;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.web.client.RestTemplate;

@Tag("regression")
@ExtendWith(MockitoExtension.class)
class KeycloakLogoutHandlerTest {

  private RestTemplate restTemplate;
  private KeycloakLogoutHandler keycloakLogoutHandler;
  private Authentication authentication;
  @Captor
  private ArgumentCaptor<String> logCaptor;
  @Mock
  private Logger logger;

  @BeforeEach
  public void setUp() {
    restTemplate = mock(RestTemplate.class);
    keycloakLogoutHandler = new KeycloakLogoutHandler(restTemplate);
    authentication = mock(Authentication.class);
    logger = mock(Logger.class);
    openMocks(this);
  }

  @Test
  void testLogoutSuccessFromKeycloak() {
    // Mocking OidcUser and OidcIdToken
    String issuer = "http://keycloak-instance/auth/realms/test";
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", "1234567890");
    claims.put("iss", issuer);
    claims.put("customClaim", "claimValue");
    OidcIdToken idToken = new OidcIdToken("testToken", Instant.now(), Instant.now().plusSeconds(60), claims);
    OidcUserAuthority authority = new OidcUserAuthority(idToken, null);
    OidcUser oidcUser = new DefaultOidcUser(Collections.singleton(authority), idToken);

    when(authentication.getPrincipal()).thenReturn(oidcUser);
    when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(
        ResponseEntity.ok("Successfully logged out"));

    keycloakLogoutHandler.logout(null, null, authentication);

    // Capture the constructed URL
    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
    verify(restTemplate).getForEntity(urlCaptor.capture(), eq(String.class));

    String actualUrl = urlCaptor.getValue();
    assertTrue(actualUrl.startsWith(issuer + "/protocol/openid-connect/logout"));
    assertTrue(actualUrl.contains("id_token_hint=testToken"));

    // Here you can validate that the restTemplate was called with the correct parameters
    verify(restTemplate).getForEntity(anyString(), eq(String.class));
    verify(restTemplate, times(1)).getForEntity(actualUrl, String.class);
  }

  @Test
  void testRestTemplateIsNull(){
    keycloakLogoutHandler = new KeycloakLogoutHandler(null);
    String issuer = "http://keycloak-instance/auth/realms/test";
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", "1234567890");
    claims.put("iss", issuer);
    claims.put("customClaim", "claimValue");
    OidcIdToken idToken = new OidcIdToken("testToken", Instant.now(), Instant.now().plusSeconds(60), claims);
    OidcUserAuthority authority = new OidcUserAuthority(idToken, null);
    OidcUser oidcUser = new DefaultOidcUser(Collections.singleton(authority), idToken);

    when(authentication.getPrincipal()).thenReturn(oidcUser);
    assertThrows(java.lang.AssertionError.class, () -> keycloakLogoutHandler.logout(null, null, authentication));
  }

  @Test
  void testLogoutErrorFromKeycloak() {
    // Mocking OidcUser and OidcIdToken
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", "1234567890");
    claims.put("customClaim", "claimValue");
    OidcIdToken idToken = new OidcIdToken("testToken", Instant.now(), Instant.now().plusSeconds(60), claims);
    OidcUserAuthority authority = new OidcUserAuthority(idToken, null);
    OidcUser oidcUser = new DefaultOidcUser(Collections.singleton(authority), idToken);

    when(authentication.getPrincipal()).thenReturn(oidcUser);
    when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(
        ResponseEntity.badRequest().body("Error logging out"));

    keycloakLogoutHandler.logout(null, null, authentication);

    // Here you can validate that the restTemplate was called with the correct parameters
    verify(restTemplate).getForEntity(anyString(), eq(String.class));
  }
}
