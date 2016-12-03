package net.alexhicks.psychicpotodo.network;

import net.alexhicks.psychicpotodo.timers.ServerClearTask;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import net.alexhicks.psychicpotodo.Constants;

/**
 * An abstraction for server-side sockets using {@link ProtocolStatement}
 *
 * @author Alex
 */
public class NetworkServer {
	public List<NetworkServerClient> clients;
	public List<ProtocolStatement> history;
	private ServerSocket socket;
	private int port;
	public long lastClearTime;
	private Timer clearTimer;

	/**
	 * Creates a new server to listen on a given port and all IP addresses
	 *
	 * @param port
	 * @throws IOException
	 */
	public NetworkServer(int port) throws IOException {
		this.port = port;
		this.socket = new ServerSocket(port);
		this.clients = new ArrayList<>();
		this.history = new ArrayList<>();
		this.clearTimer = new Timer();
		this.clearTimer.schedule(new ServerClearTask(this), 0, Constants.CLEAR_DELAY);
	}

	/**
	 * Blocks until a new connection to the server is created.
	 *
	 * @return An abstracted client socket
	 */
	public NetworkServerClient accept() {
		try {
			NetworkServerClient client = new NetworkServerClient(this.socket.accept());
			this.clients.add(client);
			ProtocolStatement timeUpdateStmt = new ProtocolStatement("lastCleared");
			timeUpdateStmt.set("millis", this.lastClearTime);
			client.send(timeUpdateStmt);
			for (ProtocolStatement stmt : this.history) {
				client.send(stmt);
			}
			return client;
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * Sends the statement to all connected clients
	 *
	 * @param stmt Statement to send
	 * @throws IOException
	 */
	public void broadcast(ProtocolStatement stmt) throws IOException {
		String data = stmt.toString();
		List<Integer> toRemove = new ArrayList<>();
		this.history.add(stmt);
		for (int i = 0; i < this.clients.size(); i++) {
			if (this.clients.get(i).isClosed()) {
				toRemove.add(i);
				continue;
			}
			this.clients.get(i).send(data);
		}
		for (Integer i : toRemove) {
			this.clients.remove(i.intValue());
		}
	}

	public void close() throws IOException {
		this.socket.close();
	}
}
