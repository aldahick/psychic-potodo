package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for a mouse button being released
 *
 * @author Alex
 */
public class MouseUpEvent extends MouseEvent {

	public MouseUpEvent(Vector2 position, int button) {
		super(MOUSE_UP, position, TYPE_UP, button);
	}

}
