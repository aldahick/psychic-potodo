package net.alexhicks.psychicpotodo.events.key;

import net.alexhicks.psychicpotodo.events.KeyEvent;

/**
 * The event for a key being pressed
 *
 * @author Alex
 */
public class KeyDownEvent extends KeyEvent {

	public KeyDownEvent(char key, boolean modifierAlt, boolean modifierCtrl, boolean modifierShift) {
		super(KEY_DOWN, TYPE_DOWN, key, modifierAlt, modifierCtrl, modifierShift);
	}
}
