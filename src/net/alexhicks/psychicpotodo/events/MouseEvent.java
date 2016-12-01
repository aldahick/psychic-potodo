package net.alexhicks.psychicpotodo.events;

import net.alexhicks.psychicpotodo.Vector2;

/**
 * The base class for all mouse events
 *
 * @author Alex
 */
public class MouseEvent extends Event {

	public static final int TYPE_DOWN = 0;
	public static final int TYPE_UP = 1;
	public static final int TYPE_MOVE = 2;
	public static final int TYPE_DRAG = 3;
	public static final int TYPE_ENTER = 4;
	public static final int TYPE_EXIT = 5;

	private Vector2 position;
	private int type, button;

	public MouseEvent(String name, Vector2 position, int type, int button) {
		super(name);
		this.position = position;
		this.type = type;
		this.button = button;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public int getType() {
		return this.type;
	}

	public int getButton() {
		return this.button;
	}

	@Override
	public String toString() {
		return "MouseEvent position=" + this.position + " type=" + this.type + " button=" + this.button;
	}
}
