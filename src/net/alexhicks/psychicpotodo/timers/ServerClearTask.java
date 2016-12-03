package net.alexhicks.psychicpotodo.timers;

import java.util.TimerTask;
import net.alexhicks.psychicpotodo.network.NetworkServer;

public class ServerClearTask extends TimerTask {
	
	private NetworkServer server;
	
	public ServerClearTask(NetworkServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		this.server.lastClearTime = System.currentTimeMillis();
		this.server.history.clear();
		// clients should clear automatically since they know the last time it happened
	}
}
