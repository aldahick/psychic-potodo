package net.alexhicks.psychicpotodo.events.mouse;

import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.MouseEvent;

/**
 * The event for the mouse being dragged
 *
 * @author Alex
 */
public class MouseDragEvent extends MouseEvent {

	public MouseDragEvent(Vector2 position) {
		super(MOUSE_DRAG, position, TYPE_DRAG, 0);
	}

}
