package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MeshTest {

    private final Mesh mesh = new Mesh();

    @Test
    void addFirstStoneToEmptyMeshShouldAddStoneAsTriangle() {
        Stone stone = new Stone(0, 0, 0);
        mesh.addFirstStone(stone);
        assertThat(mesh.getTriangles())
                .usingFieldByFieldElementComparator()
                .containsOnly(new Triangle(stone));
    }

    @Test
    void addFirstStoneToNonEmptyMeshShouldThrow() {
        mesh.addFirstStone(new Stone(0, 0, 0));
        assertThatThrownBy(() -> mesh.addFirstStone(new Stone(1, 1, 1)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("First stone was already added");
    }

    @Test
    void firstStonePlayedShouldYieldFalseForEmptyMesh() {
        assertThat(mesh.firstStonePlayed()).isFalse();
    }

    @Test
    void firstStonePlayedShouldYieldTrueForNonEmptyMesh() {
        mesh.addFirstStone(new Stone(0, 0, 0));
        assertThat(mesh.firstStonePlayed()).isTrue();
    }

}
