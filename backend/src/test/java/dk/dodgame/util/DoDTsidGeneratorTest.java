package dk.dodgame.util;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class DoDTsidGeneratorTest {

	@Test
	void constructorIsPrivateAndThrows() throws Exception {
		Constructor<DoDTsidGenerator> ctor = DoDTsidGenerator.class.getDeclaredConstructor();
		assertFalse(ctor.canAccess(null), "Constructor should be private");

		ctor.setAccessible(true);
		InvocationTargetException ex =
				assertThrows(InvocationTargetException.class, ctor::newInstance);

		// The constructor intentionally throws IllegalStateException("Utility class")
		assertInstanceOf(IllegalStateException.class, ex.getCause());
		assertEquals("Utility class", ex.getCause().getMessage());
	}


}