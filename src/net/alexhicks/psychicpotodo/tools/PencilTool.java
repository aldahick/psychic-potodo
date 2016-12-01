package net.alexhicks.psychicpotodo.tools;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.alexhicks.psychicpotodo.ClientStart;
import net.alexhicks.psychicpotodo.Constants;
import net.alexhicks.psychicpotodo.Vector2;
import net.alexhicks.psychicpotodo.events.CanvasEventListener;
import net.alexhicks.psychicpotodo.events.Event;
import net.alexhicks.psychicpotodo.events.MouseEvent;
import net.alexhicks.psychicpotodo.network.ProtocolStatement;

/**
 * Draws freeform.
 *
 * @author Alex
 */
public class PencilTool implements CanvasEventListener {

	private List<Vector2> currentPoints;
	private HashMap<String, List<Vector2>> currentNetworkPoints;
	private List<List<Vector2>> segments;
	private boolean isJustFinalized;

	public PencilTool() {
		this.currentPoints = new ArrayList<>();
		this.currentNetworkPoints = new HashMap<>();
		this.segments = new ArrayList<>();
		this.isJustFinalized = false;
	}

	@Override
	public boolean handle(Event raw) {
		if (!raw.getName().startsWith(Event.MOUSE)) {
			return false;
		}
		MouseEvent evt = (MouseEvent) raw;
		if (evt.getType() == MouseEvent.TYPE_DOWN || evt.getType() == MouseEvent.TYPE_DRAG) {
			this.currentPoints.add(evt.getPosition());
		} else if (evt.getType() == MouseEvent.TYPE_UP) {
			if (!this.currentPoints.isEmpty()) {
				this.segments.add(this.currentPoints);
				this.isJustFinalized = true;
				ProtocolStatement stmt = new ProtocolStatement("draw");
				stmt.set("drawType", "finalShape");
				stmt.set("positions", this.currentPoints.toArray());
				System.out.println(stmt.toString());
				this.currentPoints = new ArrayList<>();
				try {
					ClientStart.client.send(stmt);
				} catch (IOException ex) {
					System.err.println("IO error: " + ex.getLocalizedMessage());
				}
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void render(Graphics2D g) {
		if (this.currentPoints.size() > 0) {
			this.renderSegment(g, this.currentPoints);
		}
		for (List<Vector2> segment : this.currentNetworkPoints.values()) {
			this.renderSegment(g, segment);
		}
		for (List<Vector2> segment : this.segments) {
			this.renderSegment(g, segment);
		}
	}

	@Override
	public boolean isJustFinalized() {
		return this.isJustFinalized;
	}

	@Override
	public void undoLast() {
		if (this.segments.isEmpty()) {
			return;
		}
		this.segments.remove(this.segments.size() - 1);
	}

	private void renderSegment(Graphics2D g, List<Vector2> segment) {
		if (segment.size() > 1) {
			Vector2 lastPos = segment.get(0);
			for (int i = 1; i < segment.size(); i++) {
				Vector2 currentPos = segment.get(i);
				g.drawLine(lastPos.x, lastPos.y, currentPos.x, currentPos.y);
				lastPos = currentPos;
			}
		} else {
			g.fillRect(segment.get(0).x, segment.get(0).y, Constants.PIXEL_SIZE, Constants.PIXEL_SIZE);
		}
	}

	@Override
	public void add(Vector2[] coords, boolean isFinal, String uid) {
		List<Vector2> listCoords = Arrays.asList(coords);
		if (isFinal) {
			this.segments.add(listCoords);
		} else {
			this.currentNetworkPoints.put(uid, listCoords);
		}
	}

}
