package net.alexhicks.psychicpotodo.network;

import java.io.IOException;
import java.net.Socket;
import net.alexhicks.psychicpotodo.Constants;

/**
 * A NetworkClient class with some server-specific stuff
 *
 * @author Alex
 */
public class NetworkServerClient extends NetworkClient {

	private String selectedTool;

	public NetworkServerClient(Socket socket) throws IOException {
		super(socket);
		this.selectedTool = Constants.TOOL_PENCIL;
	}

	public String getSelectedTool() {
		return this.selectedTool;
	}

	public void setSelectedTool(String tool) {
		this.selectedTool = tool;
	}
}
