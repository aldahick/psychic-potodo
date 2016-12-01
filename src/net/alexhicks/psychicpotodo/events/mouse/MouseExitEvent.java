package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for the mouse exiting the component area
 *
 * @author Alex
 */
public class MouseExitEvent extends MouseEvent {

	public MouseExitEvent(Vector2 position, int button) {
		super(MOUSE_EXIT, position, TYPE_EXIT, button);
	}

}
