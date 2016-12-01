package net.alexhicks.psychicpotodo.events.key;

import net.alexhicks.psychicpotodo.events.KeyEvent;

/**
 * The event for a key being tapped
 *
 * @author Alex
 */
public class KeyTypeEvent extends KeyEvent {

	public KeyTypeEvent(char key, boolean modifierAlt, boolean modifierCtrl, boolean modifierShift) {
		super(KEY_TYPE, TYPE_TYPE, key, modifierAlt, modifierCtrl, modifierShift);
	}
}
