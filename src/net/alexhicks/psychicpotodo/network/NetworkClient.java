package net.alexhicks.psychicpotodo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An abstraction for client-side sockets using {@link ProtocolStatement}
 *
 * @author Alex
 */
public class NetworkClient {

	private String hostname;
	private int port;
	private Socket socket;
	protected PrintWriter out;
	protected BufferedReader in;

	/**
	 * Connects to a remote server.
	 *
	 * @param hostname The server's hostname
	 * @param port The port to connect on
	 * @throws IOException
	 */
	public NetworkClient(String hostname, int port) throws IOException {
		this.hostname = hostname;
		this.port = port;
		this.socket = new Socket(this.hostname, this.port);
		this.setupStreams();
	}

	/**
	 * Creates an abstraction layer around an existing socket
	 *
	 * @param socket The socket to abstract
	 * @throws IOException
	 */
	public NetworkClient(Socket socket) throws IOException {
		this.hostname = socket.getInetAddress().getHostName();
		this.port = socket.getPort();
		this.socket = socket;
		this.setupStreams();
	}

	private void setupStreams() throws IOException {
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
	}

	public boolean isClosed() {
		return this.socket.isClosed();
	}

	/**
	 * Sends raw data
	 *
	 * @param data The data
	 * @throws IOException
	 */
	public void send(String data) throws IOException {
		this.out.println(data);
	}

	/**
	 * Sends a {@link ProtocolStatement}
	 *
	 * @param stmt
	 * @throws IOException
	 */
	public void send(ProtocolStatement stmt) throws IOException {
		this.send(stmt.toString());
	}

	/**
	 * Blocks until data is received
	 *
	 * @return The parsed data
	 */
	public ProtocolStatement receive() {
		try {
			while (!this.in.ready()) {
			}
			String rawData = this.in.readLine();
			if (rawData == null) {
				return null;
			}
			System.out.println("Received " + rawData);
			return ProtocolStatement.fromNetworkString(rawData);
		} catch (IOException ex) {
			System.err.println("IO error: " + ex.getLocalizedMessage());
			return null;
		}
	}

	public void close() throws IOException {
		this.in.close();
		this.out.close();
		this.socket.close();
	}
}
