package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for the mouse entering the component area
 *
 * @author Alex
 */
public class MouseEnterEvent extends MouseEvent {

	public MouseEnterEvent(Vector2 position, int button) {
		super(MOUSE_ENTER, position, TYPE_ENTER, button);
	}
}
