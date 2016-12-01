package net.alexhicks.psychicpotodo;

import java.awt.BasicStroke;
import java.awt.Color;
import net.alexhicks.psychicpotodo.events.EventListener;
import net.alexhicks.psychicpotodo.events.Event;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import net.alexhicks.psychicpotodo.events.CanvasEventListener;
import net.alexhicks.psychicpotodo.events.key.KeyDownEvent;
import net.alexhicks.psychicpotodo.events.key.KeyTypeEvent;
import net.alexhicks.psychicpotodo.events.key.KeyUpEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseDownEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseDragEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseEnterEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseExitEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseMoveEvent;
import net.alexhicks.psychicpotodo.events.mouse.MouseUpEvent;
import net.alexhicks.psychicpotodo.listeners.UndoListener;
import net.alexhicks.psychicpotodo.tools.AlexTool;
import net.alexhicks.psychicpotodo.tools.AndyTool;
import net.alexhicks.psychicpotodo.tools.BrianTool;
import net.alexhicks.psychicpotodo.tools.CircleTool;
import net.alexhicks.psychicpotodo.tools.LineTool;
import net.alexhicks.psychicpotodo.tools.PencilTool;
import net.alexhicks.psychicpotodo.tools.RectangleTool;

/**
 * A panel that allows drawing.
 *
 * @author Alex
 */
public class CanvasPanel extends JPanel {

	/**
	 * The Stroke for Graphics2D contexts The only change should be a line width
	 * of {@link Constants#PIXEL_SIZE}
	 */
	protected Stroke pixelStroke;
	/**
	 * A map of registered {@link EventListener} objects used for tool events
	 */
	protected HashMap<String, CanvasEventListener> tools;
	/**
	 * Miscellaneous listeners that catch every event
	 */
	protected List<EventListener> listeners;
	protected CanvasMouseListener mouse;
	protected CanvasKeyListener key;
	/**
	 * The current tool as defined in {@link Constants}
	 */
	protected String selectedTool;

	/**
	 * The last tool used
	 */
	protected String lastTool;

	/**
	 * A buffer between tools and the parent JPanel. Used for saving images.
	 */
	public BufferedImage canvas;

	/**
	 * Populates {@link CanvasPanel#tools} along with the other instance
	 * variables.
	 */
	public CanvasPanel() {
		this.selectedTool = Constants.TOOL_PENCIL;
		this.lastTool = "";
		this.pixelStroke = new BasicStroke(Constants.PIXEL_SIZE);
		this.listeners = new ArrayList<>();
		this.listeners.add(new UndoListener(this));
		this.tools = new HashMap<>();
		this.tools.put(Constants.TOOL_PENCIL, new PencilTool());
		this.tools.put(Constants.TOOL_CIRCLE, new CircleTool());
		this.tools.put(Constants.TOOL_RECTANGLE, new RectangleTool());
		this.tools.put(Constants.TOOL_LINE, new LineTool());
		this.tools.put(Constants.TOOL_ALEX, new AlexTool());
		this.tools.put(Constants.TOOL_ANDY, new AndyTool());
		this.tools.put(Constants.TOOL_BRIAN, new BrianTool());
		this.mouse = new CanvasMouseListener();
		this.key = new CanvasKeyListener();
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addKeyListener(this.key);
	}

	/**
	 * Does nothing if the specified tool is not in {@link CanvasPanel#tools}
	 *
	 * @param tool A tool as defined in {@link Constants}
	 */
	public void setTool(String tool) {
		if (!this.tools.containsKey(tool)) {
			return;
		}
		this.selectedTool = tool;
	}

	/**
	 * Fires an event to all listeners and the selected tool.
	 *
	 * @param evt The event to fire
	 */
	public void fireEvent(Event evt) {
		CanvasEventListener tool = this.tools.get(this.selectedTool);
		boolean hasChanged = tool.handle(evt);
		if (tool.isJustFinalized()) {
			this.lastTool = this.selectedTool;
		}
		for (EventListener listener : this.listeners) {
			if (listener.handle(evt)) {
				hasChanged = true;
			}
		}
		if (hasChanged) {
			this.repaint();
		}
	}

	/**
	 * Calls the last tool's {@link CanvasEventListener#undoLast() undoLast()}
	 */
	public void undoLast() {
		if (this.lastTool.isEmpty()) {
			return;
		}
		this.tools.get(this.lastTool).undoLast();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.canvas = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = (Graphics2D) canvas.createGraphics();
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.setStroke(this.pixelStroke);
		for (String name : this.tools.keySet()) {
			Graphics2D g2dCopy = (Graphics2D) imageGraphics.create();
			this.tools.get(name).render(g2dCopy);
			g2dCopy.dispose();
		}
		imageGraphics.dispose();
		g.drawImage(canvas, 0, 0, null);
	}

	/**
	 * Listens for and fires all events for keyboard presses
	 */
	protected class CanvasKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			fireEvent(new KeyTypeEvent((char) e.getKeyCode(), e.isAltDown(), e.isControlDown(), e.isShiftDown()));
		}

		@Override
		public void keyPressed(KeyEvent e) {
			fireEvent(new KeyDownEvent((char) e.getKeyCode(), e.isAltDown(), e.isControlDown(), e.isShiftDown()));
		}

		@Override
		public void keyReleased(KeyEvent e) {
			fireEvent(new KeyUpEvent((char) e.getKeyCode(), e.isAltDown(), e.isControlDown(), e.isShiftDown()));
		}

	}

	/**
	 * Listens for and fires all events for mouse movement and actions
	 */
	protected class CanvasMouseListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			fireEvent(new MouseDownEvent(new Vector2(e.getX(), e.getY()), e.getButton()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			fireEvent(new MouseUpEvent(new Vector2(e.getX(), e.getY()), e.getButton()));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			fireEvent(new MouseEnterEvent(new Vector2(e.getX(), e.getY()), e.getButton()));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			fireEvent(new MouseExitEvent(new Vector2(e.getX(), e.getY()), e.getButton()));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			fireEvent(new MouseDragEvent(new Vector2(e.getX(), e.getY())));
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			fireEvent(new MouseMoveEvent(new Vector2(e.getX(), e.getY())));
		}

	}
}
