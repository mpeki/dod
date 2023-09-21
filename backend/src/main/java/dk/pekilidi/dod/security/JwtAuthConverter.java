package dk.pekilidi.dod.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtAuthConverterProperties properties;

  public JwtAuthConverter(JwtAuthConverterProperties properties) {
    this.properties = properties;
  }

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    Collection<GrantedAuthority> authorities = extractResourceRoles(jwt);
    return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
  }

  private String getPrincipalClaimName(Jwt jwt) {
    String claimName = JwtClaimNames.SUB;
    if (properties.getPrincipalAttribute() != null) {
      claimName = properties.getPrincipalAttribute();
    }
    return jwt.getClaim(claimName);
  }

  @SuppressWarnings("unchecked")
  private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
    Map<String, Object> resource;
    Collection<String> resourceRoles;
    if (resourceAccess == null
        || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
        || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
      return Collections.emptySet();
    }
    return resourceRoles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toSet());
  }
}