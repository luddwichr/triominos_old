package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.tile.Tile;
import com.github.luddwichr.triominos.tile.TileSet;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TileSetTest {

    @Test
    void classicTileSetContainsTilesAsDefinedByGameRule() {
        Set<Tile> expectedSet = Set.of(
                new Tile(0,0,0),
                new Tile(0,0,1),
                new Tile(0,0,2),
                new Tile(0,0,3),
                new Tile(0,0,4),
                new Tile(0,0,5),
                new Tile(0,1,1),
                new Tile(0,1,2),
                new Tile(0,1,3),
                new Tile(0,1,4),
                new Tile(0,1,5),
                new Tile(0,2,2),
                new Tile(0,2,3),
                new Tile(0,2,4),
                new Tile(0,2,5),
                new Tile(0,3,3),
                new Tile(0,3,4),
                new Tile(0,3,5),
                new Tile(0,4,4),
                new Tile(0,4,5),
                new Tile(0,5,5),
                new Tile(1,1,1),
                new Tile(1,1,2),
                new Tile(1,1,3),
                new Tile(1,1,4),
                new Tile(1,1,5),
                new Tile(1,2,2),
                new Tile(1,2,3),
                new Tile(1,2,4),
                new Tile(1,2,5),
                new Tile(1,3,3),
                new Tile(1,3,4),
                new Tile(1,3,5),
                new Tile(1,4,4),
                new Tile(1,4,5),
                new Tile(1,5,5),
                new Tile(2,2,2),
                new Tile(2,2,3),
                new Tile(2,2,4),
                new Tile(2,2,5),
                new Tile(2,3,3),
                new Tile(2,3,4),
                new Tile(2,3,5),
                new Tile(2,4,4),
                new Tile(2,4,5),
                new Tile(2,5,5),
                new Tile(3,3,3),
                new Tile(3,3,4),
                new Tile(3,3,5),
                new Tile(3,4,4),
                new Tile(3,4,5),
                new Tile(3,5,5),
                new Tile(4,4,4),
                new Tile(4,4,5),
                new Tile(4,5,5),
                new Tile(5,5,5)
        );
        assertThat(TileSet.getClassicSet())
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    void classicTileSetContainsExactly56Tiles() {
        assertThat(TileSet.getClassicSet()).hasSize(56);
    }

    @Test
    void classicTileSetIsNotModifiable() {
        assertThatThrownBy(() -> TileSet.getClassicSet().add(new Tile(1, 1, 1)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
