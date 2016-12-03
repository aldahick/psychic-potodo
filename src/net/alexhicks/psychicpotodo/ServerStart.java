package net.alexhicks.psychicpotodo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.alexhicks.psychicpotodo.network.NetworkServer;
import net.alexhicks.psychicpotodo.network.NetworkServerClient;
import net.alexhicks.psychicpotodo.network.ProtocolStatement;

/**
 * Entry point for server
 *
 * @author Alex
 */
public class ServerStart {

	/**
	 * The port the server listens on - always the same
	 */
	public static final int SERVER_PORT = 5632;

	/**
	 * The server instance
	 */
	public static NetworkServer server;

	public static void main(String[] args) {
		try {
			server = new NetworkServer(SERVER_PORT); // listens on all IPs and the default port
			System.out.println("Server started on port " + SERVER_PORT);
			List<Thread> clientThreads = new ArrayList<>(); // set up list of client socket threads
			boolean shouldContinue = true; // this may never change but it's nice to have
			while (shouldContinue) {
				NetworkServerClient client = server.accept(); // blocks
				if (client == null) {
					System.err.println("Failed to accept a client");
					continue;
				} else {
					System.out.println("Accepted client");
				}
				// setup listening thread for new client
				Thread thread = new Thread(new ClientDataListener(client));
				clientThreads.add(thread);
				thread.start();
			}
			server.close(); // close server if this loop ever ends
		} catch (IOException ex) {
			System.err.println("Server error in main(): " + ex.getLocalizedMessage());
		}
	}

	private static class ClientDataListener implements Runnable {

		private NetworkServerClient client;

		public ClientDataListener(NetworkServerClient client) {
			this.client = client;
		}

		private void handle(ProtocolStatement stmt) {
			System.out.println("Received " + stmt.toString());
			if (stmt.getAction().equals("settool")) { // this shouldn't be forwarded
				client.setSelectedTool(stmt.get("tool"));
				return;
			}
			try {
				ServerStart.server.broadcast(stmt); // forward the message to all clients
			} catch (IOException ex) {
				System.err.println("IO error: " + ex.getLocalizedMessage());
			}
		}

		@Override
		public void run() {
			while (!this.client.isClosed()) {
				ProtocolStatement stmt = this.client.receive(); // blocks
				if (stmt == null) { // error in client abstraction layer
					try {
						this.client.close();
					} catch (IOException ex) {
						System.err.println("Couldn't close client!");
					}
					continue;
				}
				if (stmt.getAction().equals("draw")) {
					stmt.set("tool", this.client.getSelectedTool()); // set tool so other clients know what to do
				}
				stmt.set("uid", this.client.hashCode()); // unique client identifier
				this.handle(stmt);
			}
		}
	}
}
