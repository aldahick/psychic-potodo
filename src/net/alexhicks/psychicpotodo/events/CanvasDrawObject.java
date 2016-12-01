package net.alexhicks.psychicpotodo.events;

import net.alexhicks.psychicpotodo.Vector2;

public class CanvasDrawObject {
	
	private Vector2[] coords;
	private String type;
	
	public CanvasDrawObject(String type, Vector2[] coords) {
		this.type = type;
		this.coords = coords;
	}
	
	public Vector2[] getCoords() {
		return this.coords;
	}
	
	public String getType() {
		return this.type;
	}
}
