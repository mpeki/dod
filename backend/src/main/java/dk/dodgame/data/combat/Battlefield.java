package dk.dodgame.data.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class Battlefield {

    private final Cell[][] grid;

    public Battlefield(int width, int height) {
        this.grid = new Cell[width][height];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // Initialize each cell, possibly with default terrain
                grid[i][j] = new Cell(Terrain.PLAINS, null, false, 0);
            }
        }
    }

    public boolean placeOccupant(Occupant occupant, int x, int y){
        Occupant existingOccupant = grid[x][y].getOccupant();
        if(existingOccupant != null || grid[x][y].isObstacle()){
            return false;
        }
        grid[x][y].setOccupant(occupant);
        return true;
    }
}