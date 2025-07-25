package dk.dodgame;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Abstract base test class for integration tests of controller components in a Spring Boot application.
 * Provides common setup and initialization for testing web controllers.
 * <p>
 * This class is annotated with {@code @SpringBootTest}, providing a full application context and allows
 * configuration of a Spring web environment with a random port for testing.
 * <p>
 * Features:
 * <p>
 * - Configures a {@code MockMvc} instance to mock web requests and responses.
 * - Automatically loads the Spring {@code WebApplicationContext} required for mocking web requests.
 * - Includes a default setup method that initializes {@code MockMvc} with Spring Security for authentication
 * and authorization during tests.
 * - Verifies the proper initialization of the web application context and {@code MockMvc} instance.
 * <p>
 * This class should be extended by test classes for specific controller groups, and provides a foundation
 * to perform comprehensive testing using mocked HTTP requests.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc   // brings in springSecurity automatically
@ActiveProfiles("test")
public abstract class BaseControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@Test
	void contextLoads() {
		assertNotNull(context);
		assertNotNull(mockMvc);
	}
}
