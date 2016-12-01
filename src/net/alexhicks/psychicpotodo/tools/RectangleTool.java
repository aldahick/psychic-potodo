package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import net.alexhicks.psychicpotodo.Vector2;

/**
 * Draws a rectangle.
 *
 * @author Alex
 */
public class RectangleTool extends ShapeTool {

	@Override
	protected void renderObject(Graphics2D g, Vector2[] coords) {
		Vector2 topLeft = coords[0].getTopLeft(coords[1]);
		Vector2 size = coords[0].copy().sub(coords[1]).abs();
		g.drawRect(topLeft.x, topLeft.y, size.x, size.y);
	}

}
