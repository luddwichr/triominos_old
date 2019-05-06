package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

	@Test
	void addStoneToNonexistentTriangle() {
		assertThatThrownBy(() -> mesh.addStone(new Stone(0, 0, 0), new Triangle(new Stone(1, 1, 1)), MatchingEdges.A_TO_A))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void addStone_A_TO_A_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(2, 1, 5);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.A_TO_A);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.A)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_A_TO_A_Not_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		mesh.addFirstStone(firstStone);

		assertThatThrownBy(() -> addSecondStone(new Stone(2, 0, 5), MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(0, 1, 5), MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_A_TO_A_AlreadyAssigned() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(2, 1, 5);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.A_TO_A);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.A_TO_A)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStone_A_TO_B_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(5, 2, 1);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.A_TO_B);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_A_TO_B_Not_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		mesh.addFirstStone(firstStone);
		assertThatThrownBy(() -> addSecondStone(new Stone(5, 2, 0), MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(5, 0, 1), MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_A_TO_B_AlreadyAssigned() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(5, 2, 1);
		mesh.addFirstStone(firstStone);
		addSecondStone(secondStone, MatchingEdges.A_TO_B);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.A_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStone_A_TO_C_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(1, 5, 2);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.A_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.A)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_A_TO_C_Not_Matching() {
		Stone firstStone = new Stone(1, 2, 5);
		mesh.addFirstStone(firstStone);
		assertThatThrownBy(() -> addSecondStone(new Stone(0, 5, 2), MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(1, 5, 0), MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_A_TO_C_AlreadyAssigned() {
		Stone firstStone = new Stone(1, 2, 5);
		Stone secondStone = new Stone(1, 5, 2);
		mesh.addFirstStone(firstStone);
		addSecondStone(secondStone, MatchingEdges.A_TO_C);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.A_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStone_B_TO_B_Matching() {
		Stone firstStone = new Stone(5, 1, 2);
		Stone secondStone = new Stone(5, 2, 1);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.B_TO_B);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.B)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_B_TO_B_Not_Matching() {
		Stone firstStone = new Stone(5, 1, 2);
		mesh.addFirstStone(firstStone);

		assertThatThrownBy(() -> addSecondStone(new Stone(5, 2, 0), MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(5, 0, 1), MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_B_TO_B_AlreadyAssigned() {
		Stone firstStone = new Stone(5, 1, 2);
		Stone secondStone = new Stone(5, 2, 1);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.B_TO_B);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.B_TO_B)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStone_B_TO_C_Matching() {
		Stone firstStone = new Stone(5, 1, 2);
		Stone secondStone = new Stone(1, 5, 2);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.B_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.B)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_B_TO_C_Not_Matching() {
		Stone firstStone = new Stone(5, 1, 2);
		mesh.addFirstStone(firstStone);

		assertThatThrownBy(() -> addSecondStone(new Stone(1, 5, 0), MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(0, 5, 2), MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_B_TO_C_AlreadyAssigned() {
		Stone firstStone = new Stone(5, 1, 2);
		Stone secondStone = new Stone(1, 5, 2);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.B_TO_C);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.B_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStone_C_TO_C_Matching() {
		Stone firstStone = new Stone(1, 5, 2);
		Stone secondStone = new Stone(2, 5, 1);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.C_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle secondTriangle = mesh.getTriangles().get(1);
		assertThat(firstTriangle.getNeighbor(Edge.C)).isSameAs(secondTriangle);
		assertThat(secondTriangle.getNeighbor(Edge.C)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getStone()).isSameAs(firstStone);
		assertThat(secondTriangle.getStone()).isSameAs(secondStone);
	}

	@Test
	void addStone_C_TO_C_Not_Matching() {
		Stone firstStone = new Stone(1, 5, 2);
		mesh.addFirstStone(firstStone);

		assertThatThrownBy(() -> addSecondStone(new Stone(0, 5, 1), MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> addSecondStone(new Stone(2, 5, 0), MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);

		assertThat(mesh.getTriangles()).hasSize(1);
	}

	@Test
	void addStone_C_TO_C_AlreadyAssigned() {
		Stone firstStone = new Stone(1, 5, 2);
		Stone secondStone = new Stone(2, 5, 1);
		mesh.addFirstStone(firstStone);

		addSecondStone(secondStone, MatchingEdges.C_TO_C);

		assertThatThrownBy(() -> addSecondStone(secondStone, MatchingEdges.C_TO_C)).isInstanceOf(IllegalStateException.class);
		assertThat(mesh.getTriangles()).hasSize(2);
	}

	@Test
	void addStoneClosingRing() {
		Stone firstStone = new Stone(0, 0, 0);
		Stone secondStone = new Stone(0, 0, 1);
		Stone thirdStone = new Stone(0, 1, 2);
		Stone fourthStone = new Stone(0, 2, 3);
		Stone fifthStone = new Stone(0, 3, 4);
		Stone sixthStone = new Stone (0, 0, 4);
		mesh.addFirstStone(firstStone);
		mesh.addStone(secondStone, mesh.getTriangles().get(0), MatchingEdges.B_TO_C);
		mesh.addStone(thirdStone, mesh.getTriangles().get(1), MatchingEdges.B_TO_C);
		mesh.addStone(fourthStone, mesh.getTriangles().get(2), MatchingEdges.B_TO_C);
		mesh.addStone(fifthStone, mesh.getTriangles().get(3), MatchingEdges.B_TO_C);
		mesh.addStone(sixthStone, mesh.getTriangles().get(4), MatchingEdges.B_TO_C);

		Triangle firstTriangle = mesh.getTriangles().get(0);
		Triangle sixthTriangle = mesh.getTriangles().get(5);
		assertThat(sixthTriangle.getNeighbor(Edge.B)).isSameAs(firstTriangle);
		assertThat(firstTriangle.getNeighbor(Edge.C)).isSameAs(sixthTriangle);
	}

	private void addSecondStone(Stone stone, MatchingEdges matchingEdges) {
		mesh.addStone(stone, mesh.getTriangles().get(0), matchingEdges);
	}

}
