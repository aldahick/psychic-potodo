package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for a mouse button being clicked
 *
 * @author Alex
 */
public class MouseDownEvent extends MouseEvent {

	public MouseDownEvent(Vector2 position, int button) {
		super(MOUSE_DOWN, position, TYPE_DOWN, button);
	}

}
