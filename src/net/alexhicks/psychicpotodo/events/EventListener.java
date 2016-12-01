package net.alexhicks.psychicpotodo.events;

public interface EventListener {

	/**
	 * Called every time any action occurs in the parent component.
	 *
	 * @param raw The event to handle (not an AWT/Swing event)
	 * @return Whether the parent component should repaint
	 */
	public abstract boolean handle(Event raw);
}
