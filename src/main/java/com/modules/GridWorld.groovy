package com.modules

import com.components.Sprite
import com.core.Entity
import com.core.IEntity
import com.core.IReturnMessage
import com.core.ReturnMessage

/**
 * Created by Tom on 10/14/2017.
 */
class GridWorld {
    class Cell {
        int x = 0, y = 0
        int tile = 0 // type id of tile
        double scale = 1.0 // TODO: limit scale > 0 and <= 1.0
        final List<IEntity> occupants = new ArrayList<>()
        int maxNumOccupants = 1 // no limit when maxNumOccupants < 0

        Cell(int x, int y, int tile) {
            this.x = x
            this.y = y
            this.tile = tile
        }

        IReturnMessage addOccupant(IEntity occupant) {
            if(maxNumOccupants > 0 && occupants.size() < maxNumOccupants) {
                occupants.add(occupant)
                return new ReturnMessage()
            } else {
                return new ReturnMessage(-1, "", "Unable to add more than " + maxNumOccupants + " occupants to cell")
            }
        }

        IReturnMessage removeOccupants() {
            occupants.clear()
            return new ReturnMessage(0, "cell cleared", "")
        }
    }

    double tileSize = 10.0 // side length
//    final Map<String, Integer> tileShapes = [
//            "square" : 0,
//            "hex" : 1,
//            "triangle" : 2
//    ]
    enum TileShape {
            SQUARE(0), HEX(1), TRIANGULAR(2) // TODO: check
    }
    TileShape tileShape = TileShape.SQUARE
    final List<List<Cell>> grid = new ArrayList<>()
    IEntity tilesetEntity = new Entity("tileset")
    int rowOffset = 0
    int colOffset = 0

    GridWorld() {
        this(10, 10, "gridworld_tileset.png")
    }

    GridWorld(int width, int height, String tileset) {
        for(int i=0; i<height; i++) {
            List<Cell> row = new ArrayList<>()
            grid.add(i, row)
            for(int j=0; j<width; j++) {
                row.add(j, new Cell(i-rowOffset, j-colOffset, 0))
            }
        }
    }

    GridWorld(int width, int height, String tileset, int[][] tilemap) {
        this(width, height, tileset)
        assert(tilemap.length == width && tilemap[0].length == height)
        for(int i=0; i<height; i++) {
            List<Cell> row = grid.get(i)
            for(int j=0; j<width; j++) {
                row.get(j).setTile(tilemap[i][j])
            }
        }
        tilesetEntity.addComponents(new Sprite(tileset))
    }

    // supports wrap-around
    Cell getCell(int x, int y) {
        x = x % grid.size()
        y = y % grid.get(x).size()
        return grid.get(x).get(y)
    }

    IReturnMessage addOccupant(int x, int y, IEntity occupant) {
        return getCell(x, y).addOccupant(occupant)
    }

    IReturnMessage removeOccupants(int x, int y) {
        return getCell(x, y).removeOccupants()
    }
}
