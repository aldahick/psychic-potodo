package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import net.alexhicks.psychicpotodo.CanvasPanel;
import net.alexhicks.psychicpotodo.Vector2;

/**
 * Draws a straight line segment from one point to another.
 *
 * @author Alex
 */
public class LineTool extends ShapeTool {
	
	public LineTool(CanvasPanel panel) {
		super(panel);
	}
	
	@Override
	protected void renderObject(Graphics2D g, Vector2[] coords) {
		g.drawLine(coords[0].x, coords[0].y, coords[1].x, coords[1].y);
	}

}
