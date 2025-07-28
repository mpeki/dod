package dk.dodgame.data.combat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import dk.dodgame.util.ExcludeFromCoverageReportGenerated;

@Builder
@Getter
@AllArgsConstructor
@ExcludeFromCoverageReportGenerated
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

    public boolean placeOccupant(Fighter fighter, int x, int y){
        Fighter existingFighter = grid[x][y].getFighter();
        if(existingFighter != null || grid[x][y].isObstacle()){
            return false;
        }
        grid[x][y].setFighter(fighter);
        return true;
    }
}