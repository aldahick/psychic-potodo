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
	 * Called for each draw object of the tool.
	 * @param g Graphics for painting
	 * @param obj The object to draw
	 */
	public abstract void render(Graphics2D g, CanvasDrawObject obj);

	/**
	 * @return If the tool has just finalized a shape
	 */
	public abstract boolean isJustFinalized();
	
	/**
	 * @return If the tool is in progress
	 */
	public abstract boolean isCurrentlyDrawing();
	
	/**
	 * Draw the current (unfinished) object
	 * @param g Graphics for painting
	 */
	public abstract void renderCurrent(Graphics2D g);
}
