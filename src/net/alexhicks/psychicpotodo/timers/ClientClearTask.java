package net.alexhicks.psychicpotodo.timers;

import java.util.TimerTask;
import net.alexhicks.psychicpotodo.CanvasPanel;

public class ClientClearTask extends TimerTask {
	
	private CanvasPanel panel;
	
	public ClientClearTask(CanvasPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void run() {
		this.panel.clear();
	}
}
