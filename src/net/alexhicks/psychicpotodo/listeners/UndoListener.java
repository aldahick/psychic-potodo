package net.alexhicks.psychicpotodo.listeners;

import net.alexhicks.psychicpotodo.CanvasPanel;
import net.alexhicks.psychicpotodo.events.Event;
import net.alexhicks.psychicpotodo.events.EventListener;
import net.alexhicks.psychicpotodo.events.key.KeyDownEvent;

/**
 * Allows users to press CTRL+Z to undo the most recent action (local only)
 *
 * @author Alex
 */
public class UndoListener implements EventListener {

	private CanvasPanel parent;

	public UndoListener(CanvasPanel parent) {
		this.parent = parent;
	}

	@Override
	public boolean handle(Event raw) {
		if (!raw.getName().equals(Event.KEY_DOWN)) {
			return false;
		}
		KeyDownEvent evt = (KeyDownEvent) raw;
		if (evt.isCtrlPressed() && evt.getKey() == 'Z') {
			this.parent.undoLast();
		}
		return true;
	}

}
