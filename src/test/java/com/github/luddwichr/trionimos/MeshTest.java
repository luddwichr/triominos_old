package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MeshTest {

    private final Mesh mesh = new Mesh();

    @Test
    void addFirstStoneShouldAddTriangleWithThisStone() {
        Stone stone = new Stone(0, 0, 0);
        mesh.addStone(stone);
        assertThat(mesh.getTriangles())
                .usingFieldByFieldElementComparator()
                .containsOnly(new Triangle(stone));
    }

    @Test
    void canAddStoneShouldYieldTrueIfMeshIsEmpty() {
        assertThat(mesh.canAddStone(new Stone(0, 0, 0))).isTrue();
    }

    @Test
    void canAddStoneShouldYieldFalseIfNotAttachableToFirstTriangle() {
        mesh.addStone(new Stone(0, 0, 0));
        assertThat(mesh.canAddStone(new Stone(1, 1, 1))).isFalse();
    }
}