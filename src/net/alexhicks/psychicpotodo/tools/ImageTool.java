package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.alexhicks.psychicpotodo.CanvasPanel;
import net.alexhicks.psychicpotodo.Vector2;

/**
 * Designed to make image-based drawing tools simple to build.
 *
 * @author Alex
 */
public class ImageTool extends ShapeTool {

	private Image image;

	public ImageTool(CanvasPanel panel, String filename) {
		super(panel);
		try {
			this.image = ImageIO.read(new File(filename));
		} catch (IOException ex) {
			System.err.println("Could not load image " + filename + "!");
		}
	}

	@Override
	protected void renderObject(Graphics2D g, Vector2[] coords) {
		Vector2 topLeft = coords[0].getTopLeft(coords[1]);
		Vector2 size = coords[0].copy().sub(coords[1]).abs();
		g.drawImage(this.image, topLeft.x, topLeft.y, size.x, size.y, null);
	}
}
