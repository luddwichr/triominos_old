package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MeshTest {

	private final Mesh mesh = new Mesh();

	@Test
	void addFirstTileToEmptyMeshShouldAddTileAsTriangle() {
		Tile tile = new Tile(0, 0, 0);
		mesh.addFirstTile(tile);
		assertThat(mesh.getTriangles())
				.usingFieldByFieldElementComparator()
				.containsOnly(new Triangle(tile));
	}

	@Test
	void addFirstTileToNonEmptyMeshShouldThrow() {
		mesh.addFirstTile(new Tile(0, 0, 0));
		assertThatThrownBy(() -> mesh.addFirstTile(new Tile(1, 1, 1)))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("First tile was already added");
	}

	@Test
	void firstTilePlayedShouldYieldFalseForEmptyMesh() {
		assertThat(mesh.firstTilePlayed()).isFalse();
	}

	@Test
	void firstTilePlayedShouldYieldTrueForNonEmptyMesh() {
		mesh.addFirstTile(new Tile(0, 0, 0));
		assertThat(mesh.firstTilePlayed()).isTrue();
	}

	@Test
	void addTileToNonexistentTriangle() {
		assertThatThrownBy(() -> mesh.addTile(new Tile(0, 0, 0), new Triangle(new Tile(1, 1, 1)), MatchingEdges.A_TO_A))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void addTile_A_TO_A_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(2, 1, 5);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.A_TO_A);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.A)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_A_TO_A_Not_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		mesh.addFirstTile(firstTile);

		assertThatThrownBy(() -> addSecondTile(new Tile(2, 0, 5), MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(0, 1, 5), MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_A_TO_A_AlreadyAssigned() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(2, 1, 5);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.A_TO_A);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTile_A_TO_B_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(5, 2, 1);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.A_TO_B);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_A_TO_B_Not_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		mesh.addFirstTile(firstTile);
		assertThatThrownBy(() -> addSecondTile(new Tile(5, 2, 0), MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(5, 0, 1), MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_A_TO_B_AlreadyAssigned() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(5, 2, 1);
		mesh.addFirstTile(firstTile);
		addSecondTile(secondTile, MatchingEdges.A_TO_B);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTile_A_TO_C_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(1, 5, 2);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.A_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_A_TO_C_Not_Matching() {
		Tile firstTile = new Tile(1, 2, 5);
		mesh.addFirstTile(firstTile);
		assertThatThrownBy(() -> addSecondTile(new Tile(0, 5, 2), MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(1, 5, 0), MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_A_TO_C_AlreadyAssigned() {
		Tile firstTile = new Tile(1, 2, 5);
		Tile secondTile = new Tile(1, 5, 2);
		mesh.addFirstTile(firstTile);
		addSecondTile(secondTile, MatchingEdges.A_TO_C);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTile_B_TO_B_Matching() {
		Tile firstTile = new Tile(5, 1, 2);
		Tile secondTile = new Tile(5, 2, 1);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.B_TO_B);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.B)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_B_TO_B_Not_Matching() {
		Tile firstTile = new Tile(5, 1, 2);
		mesh.addFirstTile(firstTile);

		assertThatThrownBy(() -> addSecondTile(new Tile(5, 2, 0), MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(5, 0, 1), MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_B_TO_B_AlreadyAssigned() {
		Tile firstTile = new Tile(5, 1, 2);
		Tile secondTile = new Tile(5, 2, 1);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.B_TO_B);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTile_B_TO_C_Matching() {
		Tile firstTile = new Tile(5, 1, 2);
		Tile secondTile = new Tile(1, 5, 2);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.B_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.B)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_B_TO_C_Not_Matching() {
		Tile firstTile = new Tile(5, 1, 2);
		mesh.addFirstTile(firstTile);

		assertThatThrownBy(() -> addSecondTile(new Tile(1, 5, 0), MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(0, 5, 2), MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_B_TO_C_AlreadyAssigned() {
		Tile firstTile = new Tile(5, 1, 2);
		Tile secondTile = new Tile(1, 5, 2);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.B_TO_C);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTile_C_TO_C_Matching() {
		Tile firstTile = new Tile(1, 5, 2);
		Tile secondTile = new Tile(2, 5, 1);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.C_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.C)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getTile()).isSameAs(firstTile);
		assertThat(secondTriangle.getTile()).isSameAs(secondTile);
	}

	@Test
	void addTile_C_TO_C_Not_Matching() {
		Tile firstTile = new Tile(1, 5, 2);
		mesh.addFirstTile(firstTile);

		assertThatThrownBy(() -> addSecondTile(new Tile(0, 5, 1), MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondTile(new Tile(2, 5, 0), MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addTile_C_TO_C_AlreadyAssigned() {
		Tile firstTile = new Tile(1, 5, 2);
		Tile secondTile = new Tile(2, 5, 1);
		mesh.addFirstTile(firstTile);

		addSecondTile(secondTile, MatchingEdges.C_TO_C);

		assertThatThrownBy(() -> addSecondTile(secondTile, MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addTileClosingRing() {
		Tile firstTile = new Tile(0, 0, 0);
		Tile secondTile = new Tile(0, 1, 0);
		Tile thirdTile = new Tile(1, 1, 0);
		Tile fourthTile = new Tile(1, 2, 0);
		Tile fifthTile = new Tile(2, 2, 0);
		Tile sixthTile = new Tile(2, 0, 0);
		mesh.addFirstTile(firstTile);
		mesh.addTile(secondTile, mesh.getTriangles().get(0), MatchingEdges.B_TO_C);
		mesh.addTile(thirdTile, mesh.getTriangles().get(1), MatchingEdges.B_TO_C);
		mesh.addTile(fourthTile, mesh.getTriangles().get(2), MatchingEdges.B_TO_C);
		mesh.addTile(fifthTile, mesh.getTriangles().get(3), MatchingEdges.B_TO_C);
		mesh.addTile(sixthTile, mesh.getTriangles().get(4), MatchingEdges.B_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle sixthTriangle = mesh.getTriangles().get(5);
		assertThat(sixthTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getNeighbor(Edge.C)).isSameAs(sixthTriangle);
	}

	private void addSecondTile(Tile tile, MatchingEdges matchingEdges) {
		mesh.addTile(tile, mesh.getTriangles().get(0), matchingEdges);
	}

}
