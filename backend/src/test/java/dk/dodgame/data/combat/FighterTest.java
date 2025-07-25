package dk.dodgame.data.combat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dk.dodgame.data.CharacterDTO;

/**
 * Focuses on the public API of {@link Fighter}.
 */
@Tag("regression")
class FighterTest {

	/* -------------------------------------------------------------
	 * getFighterId / getActorType
	 * ------------------------------------------------------------- */
	@Test
	void getFighterId_DelegatesToCharacter() {
		CharacterDTO character = mock(CharacterDTO.class);
		when(character.getId()).thenReturn("char-123");

		Fighter fighter = Fighter.builder()
				.character(character)
				.movement(mock(Movement.class))
				.actions(Collections.emptyList())
				.side("A")
				.build();

		assertThat(fighter.getFighterId()).isEqualTo("char-123");
	}

	@Test
	void getActorType_AlwaysReturnsFighter() {
		Fighter fighter = Fighter.builder()
				.character(mock(CharacterDTO.class))
				.movement(mock(Movement.class))
				.actions(Collections.emptyList())
				.side("A")
				.build();

		assertThat(fighter.getActorType()).isEqualTo("fighter");
	}

	/* -------------------------------------------------------------
	 * changeSpeed – behaviour
	 * ------------------------------------------------------------- */
/*
	@Test
	void changeSpeed_IncreasesSpeedButNotBeyondMax() {
		//  initial speed = 5, max in AIR = 20, modifier = +30  -> capped to 20
		Movement movement = mock(Movement.class);
		when(movement.getSpeed()).thenReturn(5);

		Cell cell = mock(Cell.class);
		when(cell.getTerrain()).thenReturn(Terrain.AIR);   // chooses getSpeedInAir()

		when(movement.getSpeed()).thenReturn(20);

		CharacterDTO character = mock(CharacterDTO.class);

		Fighter fighter = Fighter.builder()
				.character(character)
				.movement(movement)
				.actions(Collections.emptyList())
				.side("A")
				.build();

		fighter.changeSpeed(+30, cell);

		verify(movement).setSpeed(20);          // capped
	}

	@Test
	void changeSpeed_IncreasesSpeedWithinMax() {
		//  initial speed = 2, max on LAND = 10, modifier = +3 -> new speed = 5
		Movement movement = mock(Movement.class);
		when(movement.getSpeed()).thenReturn(2);

		Cell cell = mock(Cell.class);
		when(cell.getTerrain()).thenReturn(Terrain.CITY); // hits default branch

		Movement mp = mock(Movement.class);
		when(mp.getSpeed()).thenReturn(10);

		CharacterDTO character = mock(CharacterDTO.class);

		Fighter fighter = Fighter.builder()
				.character(character)
				.movement(movement)
				.actions(Collections.emptyList())
				.side("B")
				.build();

		fighter.changeSpeed(+3, cell);

		verify(movement).setSpeed(5);
	}

	@Test
	void changeSpeed_NegativeResultLeavesSpeedUnchanged() {
		//  initial speed = 4, modifier = -10  -> negative => ignore
		Movement movement = mock(Movement.class);
		when(movement.getSpeed()).thenReturn(4);

		Cell cell = mock(Cell.class);
		when(cell.getTerrain()).thenReturn(Terrain.WATER);

		Movement mp = mock(Movement.class);
		when(mp.getSpeed()).thenReturn(8);

		CharacterDTO character = mock(CharacterDTO.class);

		Fighter fighter = Fighter.builder()
				.character(character)
				.movement(movement)
				.actions(Collections.emptyList())
				.side("C")
				.build();

		fighter.changeSpeed(-10, cell);

		// setSpeed must never be invoked
		verify(movement, never()).setSpeed(anyInt());
	}

	*/
/* -------------------------------------------------------------
	 * Helper – capturing the value set on the movement mock
	 * ------------------------------------------------------------- *//*

	@Test
	void changeSpeed_SetsExpectedValue_CaptureExample() {
		Movement movement = mock(Movement.class);
		when(movement.getSpeed()).thenReturn(1);

		Cell cell = mock(Cell.class);
		when(cell.getTerrain()).thenReturn(Terrain.RIVER);

		Movement mp = mock(Movement.class);
		when(mp.getSpeed()).thenReturn(6);

		CharacterDTO character = mock(CharacterDTO.class);

		Fighter fighter = Fighter.builder()
				.character(character)
				.movement(movement)
				.actions(Collections.emptyList())
				.side("D")
				.build();

		fighter.changeSpeed(+2, cell); // 1 + 2 = 3, still below max(6)

		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		verify(movement).setSpeed(captor.capture());
		assertThat(captor.getValue()).isEqualTo(3);
	}
*/
}