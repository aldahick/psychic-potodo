package net.alexhicks.psychicpotodo.events.key;

import net.alexhicks.psychicpotodo.events.KeyEvent;

/**
 * The event for a key being released
 *
 * @author Alex
 */
public class KeyUpEvent extends KeyEvent {

	public KeyUpEvent(char key, boolean modifierAlt, boolean modifierCtrl, boolean modifierShift) {
		super(KEY_UP, TYPE_UP, key, modifierAlt, modifierCtrl, modifierShift);
	}
}
