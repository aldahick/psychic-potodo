package net.alexhicks.psychicpotodo.events;

/**
 * The base class for all Potodo key events
 *
 * @author Alex
 */
public class KeyEvent extends Event {

	public static final int TYPE_DOWN = 0;
	public static final int TYPE_TYPE = 1;
	public static final int TYPE_UP = 2;

	private int type;

	private char key;

	private boolean modifierAlt, modifierCtrl, modifierShift;

	public KeyEvent(String name, int type, char key, boolean modifierAlt, boolean modifierCtrl, boolean modifierShift) {
		super(name);
		this.type = type;
		this.key = key;
		this.modifierAlt = modifierAlt;
		this.modifierCtrl = modifierCtrl;
		this.modifierShift = modifierShift;
	}

	public int getType() {
		return this.type;
	}

	public char getKey() {
		return this.key;
	}

	public boolean isAltPressed() {
		return this.modifierAlt;
	}

	public boolean isCtrlPressed() {
		return this.modifierCtrl;
	}

	public boolean isShiftPressed() {
		return this.modifierShift;
	}

	@Override
	public String toString() {
		return "KeyEvent type=" + this.type + " key=" + this.key + " alt="
				+ this.modifierAlt + " ctrl=" + this.modifierCtrl
				+ " shift=" + this.modifierShift;
	}
}
