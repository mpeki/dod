package dk.pekilidi.dod.security;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  public static final String SYSTEM = "system";
  public static final String DUNGEON_MASTER = "master";
  public static final String PLAYER = "player";
  public static final String NPC = "npc";

  private final JwtAuthConverter jwtAuthConverter;

  public WebSecurityConfig(JwtAuthConverter jwtAuthConverter) {
    this.jwtAuthConverter = jwtAuthConverter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/health", "/api/skill/**", "/api/items", "/api/race").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/items/type/**").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.GET, "/api/items/key/**").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.POST, "/api/items/**").hasAnyRole(DUNGEON_MASTER, SYSTEM)
        .requestMatchers(HttpMethod.GET, "/api/race/**").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.GET, "/api/char", "/api/char/**").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.POST, "/api/char").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.POST, "/api/char/bulk/**").hasAnyRole(DUNGEON_MASTER, SYSTEM)
        .requestMatchers(HttpMethod.POST, "/api/change/char/*").hasAnyRole(DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.POST, "/api/action/training/**").hasAnyRole(SYSTEM, DUNGEON_MASTER, PLAYER)
        .requestMatchers(HttpMethod.GET, "/info", "/env", "/liquibase").hasAnyRole(SYSTEM)
        .requestMatchers(HttpMethod.DELETE, "/caches/clear").hasRole(SYSTEM)
        .anyRequest()
        .authenticated();
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
