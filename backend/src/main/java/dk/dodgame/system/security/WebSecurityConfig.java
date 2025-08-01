package dk.dodgame.system.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	public static final String SYSTEM = "system";
	public static final String DUNGEON_MASTER = "master";
	public static final String PLAYER = "player";
	private final JwtAuthConverter jwtAuthConverter;

	public WebSecurityConfig(JwtAuthConverter jwtAuthConverter) {
		this.jwtAuthConverter = jwtAuthConverter;
	}



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Apply CORS configuration
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(requestMatcher("GET", "/docs/**")).permitAll()
						.requestMatchers(requestMatcher("OPTIONS", "/**")).permitAll()
						.requestMatchers(requestMatcher("GET", "/api/info", "/api/health", "/api/skill/**", "/api/items", "/api/race")).permitAll()
						.requestMatchers(requestMatcher("GET", "/api/items/type/**")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("GET", "/api/items/key/**")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("POST", "/api/items/**")).hasAnyRole(DUNGEON_MASTER, SYSTEM)
						.requestMatchers(requestMatcher("GET", "/api/race/**")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("GET", "/api/char", "/api/char/**")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("DELETE", "/api/char/**")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("POST", "/api/char")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("POST", "/api/char/bulk/**")).hasAnyRole(DUNGEON_MASTER, SYSTEM)
						.requestMatchers(requestMatcher("POST", "/api/change/char/*")).hasAnyRole(DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("POST", "/api/action/training/**")).hasAnyRole(SYSTEM, DUNGEON_MASTER, PLAYER)
						.requestMatchers(requestMatcher("GET", "/info", "/env", "/liquibase")).hasAnyRole(SYSTEM)
						.requestMatchers(requestMatcher("DELETE", "/caches/clear")).hasRole(SYSTEM)
						.anyRequest()
						.authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	private RequestMatcher requestMatcher(String httpMethod, String... patterns) {
		HttpMethod method = HttpMethod.valueOf(httpMethod);

		RequestMatcher[] matchers = Arrays.stream(patterns)
				.map(pattern ->
						PathPatternRequestMatcher.withPathPatternParser(new PathPatternParser()).matcher(method, pattern))
				.toArray(RequestMatcher[]::new);

		return new OrRequestMatcher(matchers);
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
