package net.alexhicks.psychicpotodo.events;

import java.awt.Graphics2D;
import net.alexhicks.psychicpotodo.Vector2;

/**
 * The interface all tools should implement
 *
 * @author Alex
 */
public interface CanvasEventListener extends EventListener {

	/**
	 * Called every time the parent component paints/repaints.
	 *
	 * @param g Graphics for painting
	 */
	public abstract void render(Graphics2D g);

	/**
	 * @return If the tool has just finalized a shape
	 */
	public abstract boolean isJustFinalized();

	/**
	 * Undoes the last segment this tool made.
	 */
	public abstract void undoLast();

	/**
	 * Adds a segment from the server.
	 *
	 * @param coords The coordinates of the segment
	 * @param isFinal If the segment is final (should be true)
	 * @param uid The unique identifier of the segment's author
	 */
	public abstract void add(Vector2[] coords, boolean isFinal, String uid);
}
