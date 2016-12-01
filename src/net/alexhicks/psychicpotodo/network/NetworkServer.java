package net.alexhicks.psychicpotodo.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstraction for server-side sockets using {@link ProtocolStatement}
 *
 * @author Alex
 */
public class NetworkServer {

	public List<NetworkServerClient> clients;
	private ServerSocket socket;
	private int port;

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
		System.out.println("Broadcasting...");
		List<Integer> toRemove = new ArrayList<>();
		for (int i = 0; i < this.clients.size(); i++) {
			if (this.clients.get(i).isClosed()) {
				toRemove.add(i);
			}
			this.clients.get(i).send(data);
			System.out.println("Sending data to client " + this.clients.get(i).hashCode());
		}
		for (Integer i : toRemove) {
			this.clients.remove(i.intValue());
		}
		System.out.println("End broadcast.");
	}

	public void close() throws IOException {
		this.socket.close();
	}
}
