package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TriangleTest {

	@Test
	void getStone() {
		Stone stone = new Stone(0, 0, 0);
		assertThat(new Triangle(stone).getStone()).isSameAs(stone);
	}

	@Test
	void attach_AB_TO_BC_Matching() {
		Triangle triangle = new Triangle(new Stone(0, 0, 0));
		Triangle triangleToAttach = new Triangle(new Stone(0, 0,  1));
		triangle.attach(triangleToAttach, MatchingEdges.AB_TO_BC);
		assertThat(triangle.neighborBC()).isSameAs(triangleToAttach);
		assertThat(triangleToAttach.neighborAB()).isSameAs(triangle);
	}

	@Test
	void attach_AB_TO_BC_NotMatching() {
		Triangle triangle = new Triangle(new Stone(0, 0, 0));
		Triangle triangleToAttach = new Triangle(new Stone(1, 0, 0));
		assertThatThrownBy(() -> triangle.attach(triangleToAttach, MatchingEdges.AB_TO_BC))
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	void attach_AB_TO_BC_AlreadyAssigned_BC() {
		Triangle triangle = new Triangle(new Stone(0, 0, 0));
		Triangle alreadyAttachedTriangle = new Triangle(new Stone(0, 0,  1));
		Triangle triangleToAttach = new Triangle(new Stone(0, 0,  2));
		triangle.attach(alreadyAttachedTriangle, MatchingEdges.AB_TO_BC);
		assertThatThrownBy(() -> triangle.attach(triangleToAttach, MatchingEdges.AB_TO_BC))
				.isInstanceOf(IllegalStateException.class);

	}
}
