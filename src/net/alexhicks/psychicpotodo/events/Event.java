package net.alexhicks.psychicpotodo.events;

/**
 * The base class for all Potodo events
 *
 * @author Alex
 */
public abstract class Event {

	/**
	 * String identifiers for events
	 */
	public static final String KEY = "key_";
	public static final String KEY_DOWN = "key_down";
	public static final String KEY_TYPE = "key_type";
	public static final String KEY_UP = "key_up";
	public static final String MOUSE = "mouse_";
	public static final String MOUSE_DOWN = "mouse_down";
	public static final String MOUSE_DRAG = "mouse_drag";
	public static final String MOUSE_ENTER = "mouse_enter";
	public static final String MOUSE_EXIT = "mouse_exit";
	public static final String MOUSE_MOVE = "mouse_move";
	public static final String MOUSE_UP = "mouse_up";

	/**
	 * The event name
	 */
	private final String name;

	public Event(String name) {
		this.name = name;
	}

	/**
	 * @return The identifier of the event
	 */
	public final String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
