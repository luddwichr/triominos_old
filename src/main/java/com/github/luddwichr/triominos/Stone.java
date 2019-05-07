package com.github.luddwichr.triominos;

/*
 *      c
 *     / \
 *    /   \
 *   /     \
 *  a-------b
 *
 */
public class Stone {

	public final int a;
	public final int b;
	public final int c;

	public Stone(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public int points() {
		return a + b + c;
	}

	@Override
	public String toString() {
		return "Stone{a=" + a + ", b=" + b + ", c=" + c + '}';
	}
}
