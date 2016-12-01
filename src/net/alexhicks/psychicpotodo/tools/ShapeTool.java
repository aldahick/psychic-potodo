package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Arrays;
import net.alexhicks.psychicpotodo.CanvasPanel;
import net.alexhicks.psychicpotodo.ClientStart;
import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.CanvasDrawObject;
import net.alexhicks.psychicpotodo.events.CanvasEventListener;
import net.alexhicks.psychicpotodo.events.Event;
import net.alexhicks.psychicpotodo.events.MouseEvent;
import net.alexhicks.psychicpotodo.network.ProtocolStatement;

/**
 * Used to simplify creation of tools that will always have the same shape
 * structure
 *
 * @author Alex
 */
public abstract class ShapeTool implements CanvasEventListener {

//	private List<Vector2[]> shapes;
//	private HashMap<String, Vector2[]> currentNetwork;
	private Vector2[] current;
	private boolean isFollowing;
	private boolean isJustFinalized;
	private CanvasPanel panel;

	public ShapeTool(CanvasPanel panel) {
		this.panel = panel;
		this.current = new Vector2[]{new Vector2(), new Vector2()};
		this.isFollowing = false;
		this.isJustFinalized = false;
	}

	@Override
	public boolean handle(Event raw) {
		if (!raw.getName().startsWith(Event.MOUSE)) {
			return false;
		}
		MouseEvent evt = (MouseEvent) raw;
		if (evt.getType() == MouseEvent.TYPE_DOWN) {
			this.current[0] = evt.getPosition();
		} else if (evt.getType() == MouseEvent.TYPE_DRAG) {
			this.current[1] = evt.getPosition();
			this.isFollowing = true;
		} else if (evt.getType() == MouseEvent.TYPE_UP) {
			if (!this.isFollowing) {
				return false;
			}
			this.isFollowing = false;
			this.isJustFinalized = true;
			this.panel.addDrawObject(this.current);
			ProtocolStatement stmt = new ProtocolStatement("draw");
			stmt.set("drawType", "finalShape");
			stmt.set("positions", this.current);
			System.out.println(stmt.toString());
			try {
				ClientStart.client.send(stmt);
			} catch (IOException ex) {
				System.err.println("IO error: " + ex.getLocalizedMessage());
			}
			this.current = new Vector2[]{new Vector2(), new Vector2()};
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isCurrentlyDrawing() {
		return this.isFollowing;
	}

	/*
	@Override
	public void render(Graphics2D g) {
		if (this.isFollowing) {
			this.renderObject(g, this.current);
		}
		for (Vector2[] shape : this.currentNetwork.values()) {
			this.renderObject(g, shape);
		}
		for (Vector2[] shape : this.shapes) {
			this.renderObject(g, shape);
		}
	}
	*/
	@Override
	public void render(Graphics2D g, CanvasDrawObject obj) {
		this.renderObject(g, obj.getCoords());
	}
	
	@Override
	public void renderCurrent(Graphics2D g) {
		this.renderObject(g, this.current);
	}

	@Override
	public boolean isJustFinalized() {
		return this.isJustFinalized;
	}

	protected abstract void renderObject(Graphics2D g, Vector2[] coords);
}
