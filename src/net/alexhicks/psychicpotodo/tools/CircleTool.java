package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import net.alexhicks.psychicpotodo.Vector2;

/**
 * Draws ellipses. The center of the circle is in the middle of the starting and
 * ending positions (where the mouse was initially clicked and where the button
 * was released)
 *
 * @author Alex
 */
public class CircleTool extends ShapeTool {

	@Override
	protected void renderObject(Graphics2D g, Vector2[] coords) {
		Vector2 topLeft = coords[0].getTopLeft(coords[1]);
		Vector2 size = coords[0].copy().sub(coords[1]).abs();
		g.drawOval(topLeft.x, topLeft.y, size.x, size.y);
	}

}
