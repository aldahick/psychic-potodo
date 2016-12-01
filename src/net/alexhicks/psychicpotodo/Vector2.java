package net.alexhicks.psychicpotodo;

import java.util.Arrays;

/**
 * 2-dimensional vector with some math operations.
 *
 * @author Alex
 */
public class Vector2 {

	public int x, y;

	/**
	 * Initializes x and y to 0
	 */
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * @param both The value of x and y
	 */
	public Vector2(int both) {
		this.x = both;
		this.y = both;
	}

	/**
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return A new {@link Vector2} with the same properties as this one
	 */
	public Vector2 copy() {
		return new Vector2(this.x, this.y);
	}

	/**
	 * Sets x and y to their respective absolute values
	 *
	 * @return This (for chaining)
	 */
	public Vector2 abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		return this;
	}

	public Vector2 add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	/**
	 * @param both The value to add to x and y
	 * @return This (for chaining)
	 */
	public Vector2 add(int both) {
		return this.add(new Vector2(both));
	}

	public Vector2 sub(Vector2 other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	/**
	 * @param both The value to subtract from x and y
	 * @return This (for chaining)
	 */
	public Vector2 sub(int both) {
		return this.sub(new Vector2(both));
	}

	public Vector2 mul(Vector2 other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	/**
	 * @param both The value to multiply with x and y
	 * @return This (for chaining)
	 */
	public Vector2 mul(int both) {
		return this.mul(new Vector2(both));
	}

	public Vector2 div(Vector2 other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}

	/**
	 * @param both The value to divide x and y by
	 * @return This (for chaining)
	 */
	public Vector2 div(int both) {
		return this.div(new Vector2(both));
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Vector2)) {
			return false;
		}
		return this.hashCode() == ((Vector2) other).hashCode();
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + this.x;
		hash = 53 * hash + this.y;
		return hash;
	}

	/**
	 * Gets directional components of a line starting at this and ending at
	 * other
	 *
	 * @param other The end of the line
	 * @return Horizontal, Vertical directions ([0], [1])
	 */
	public Direction[] getDirectionComponents(Vector2 other) {
		Direction[] components = new Direction[]{
			Direction.RIGHT, Direction.DOWN
		};
		if (this.equals(other)) {
			return components;
		}
		boolean moreX = this.x > other.x;
		boolean moreY = this.y > other.y;
		if (moreX) {
			components[0] = Direction.LEFT;
		}
		if (moreY) {
			components[1] = Direction.UP;
		}
		return components;
	}

	/**
	 * Calculates the top left corner in a rectangle of which this and other are
	 * opposite corners.
	 *
	 * @param other The opposite corner from this
	 * @return The coordinates of the top left corner
	 */
	public Vector2 getTopLeft(Vector2 other) {
		Direction[] components = this.getDirectionComponents(other);
		Vector2 topLeft = this.copy();
		if (components[0] == Direction.LEFT) {
			topLeft.x = other.x;
		}
		if (components[1] == Direction.UP) {
			topLeft.y = other.y;
		}
		return topLeft;
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	/**
	 * Format: (x,y)
	 *
	 * @param raw Raw string to convert
	 * @return A new instance
	 */
	public static Vector2 fromString(String raw) {
		String[] tokens = raw.substring(1, raw.length() - 1).split(",");
		try {
			int x = Integer.parseInt(tokens[0]);
			int y = Integer.parseInt(tokens[1]);
			return new Vector2(x, y);
		} catch (NumberFormatException ex) {
			System.out.println(Arrays.toString(tokens));
			return null;
		}
	}
}
