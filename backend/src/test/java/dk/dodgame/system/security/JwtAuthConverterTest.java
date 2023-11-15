package dk.dodgame.system.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import dk.dodgame.system.security.JwtAuthConverter;
import dk.dodgame.system.security.JwtAuthConverterProperties;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@Tag("regression")
class JwtAuthConverterTest {

  private Jwt jwt;
  private JwtAuthConverterProperties properties;

  @BeforeEach
  void setUp() {
    jwt = Mockito.mock(Jwt.class);
    properties = new JwtAuthConverterProperties();
    properties.setPrincipalAttribute("principalAttribute");
    properties.setResourceId("myResource");

    when(jwt.getClaim("principalAttribute")).thenReturn("principalName");


  }

  @Test
  void testConvertPrincipalName() {

    mockJwtResourceAccessClaim(Arrays.asList("ROLE1", "ROLE2"));

    JwtAuthConverter converter = new JwtAuthConverter(properties);
    AbstractAuthenticationToken token = converter.convert(jwt);

    assertNotNull(token);
    assertEquals("principalName", token.getName());
    assertEquals(2, token.getAuthorities().size());
    assertTrue(token.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROLE1")));
    assertTrue(token.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROLE2")));
  }

  @Test
  void testConvertPrincipalNameNullPrincipalAtt() {

    mockJwtResourceAccessClaim(Arrays.asList("ROLE1", "ROLE2"));
    properties.setPrincipalAttribute(null);
    JwtAuthConverter converter = new JwtAuthConverter(properties);
    AbstractAuthenticationToken token = converter.convert(jwt);

    assertNotNull(token);
    assertEquals(null, token.getName());
    assertEquals(2, token.getAuthorities().size());
    assertTrue(token.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROLE1")));
    assertTrue(token.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROLE2")));
  }


  @Test
  void testConvertNullResources() {

    JwtAuthConverter converter = new JwtAuthConverter(properties);
    AbstractAuthenticationToken token = converter.convert(jwt);

    assertNotNull(token);
    assertEquals("principalName", token.getName());
    assertEquals(0, token.getAuthorities().size());
  }

  @Test
  void testConvertEmptyResources() {

    when(jwt.getClaim("resource_access")).thenReturn(new HashMap<>());
    JwtAuthConverter converter = new JwtAuthConverter(properties);
    AbstractAuthenticationToken token = converter.convert(jwt);

    assertNotNull(token);
    assertEquals("principalName", token.getName());
    assertEquals(0, token.getAuthorities().size());
  }

  @Test
  void testConvertNullRolesResources() {

    Map<String, Object> myResource = new HashMap<>();
    myResource.put("roles", null);
    when(jwt.getClaim("resource_access")).thenReturn(myResource);
    JwtAuthConverter converter = new JwtAuthConverter(properties);
    AbstractAuthenticationToken token = converter.convert(jwt);

    assertNotNull(token);
    assertEquals("principalName", token.getName());
    assertEquals(0, token.getAuthorities().size());
  }



  private void mockJwtResourceAccessClaim(List<String> roles) {
    Map<String, Object> resourceAccess = new HashMap<>();
    Map<String, Object> myResource = new HashMap<>();
    myResource.put("roles", roles);
    resourceAccess.put("myResource", myResource);
    when(jwt.getClaim("resource_access")).thenReturn(resourceAccess);
  }

  // More tests to handle edge cases
  // ...

}
