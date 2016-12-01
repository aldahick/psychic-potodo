package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for the mouse moving
 *
 * @author Alex
 */
public class MouseMoveEvent extends MouseEvent {

	public MouseMoveEvent(Vector2 position) {
		super(MOUSE_MOVE, position, TYPE_MOVE, 0);
	}
}
