package net.alexhicks.psychicpotodo.timers;

import java.util.TimerTask;
import net.alexhicks.psychicpotodo.ClientStart;

public class ClientRenderTask extends TimerTask {

	@Override
	public void run() {
		ClientStart.panel.repaint();
	}

}
