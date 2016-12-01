package net.alexhicks.psychicpotodo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import net.alexhicks.psychicpotodo.network.NetworkClient;
import net.alexhicks.psychicpotodo.network.ProtocolStatement;

/**
 * Entry point for client (requires Swing support)
 *
 * @author Alex
 */
public class ClientStart {

	/**
	 * The base URL from which all assets should be downloaded.
	 */
	public static final String ASSET_URL = "https://cs.iupui.edu/~aldahick/homework/csci-240/";

	/**
	 * A list of assets to be downloaded if they do not already exist.
	 */
	public static final String[] ASSETS = {"alex.png", "andy.png", "brian.png"};

	/**
	 * A frame to allow the user to enter the server hostname.
	 */
	public static ConnectFrame connectFrame;

	/**
	 * The main panel for drawing.
	 */
	public static CanvasPanel panel;

	/**
	 * The (slightly abstracted) socket used to communicate with the server.
	 */
	public static NetworkClient client;

	/**
	 * All tools to add buttons for.
	 */
	public static String[] tools = new String[]{
		Constants.TOOL_PENCIL,
		Constants.TOOL_CIRCLE,
		Constants.TOOL_RECTANGLE,
		Constants.TOOL_LINE,
		Constants.TOOL_ALEX,
		Constants.TOOL_ANDY,
		Constants.TOOL_BRIAN
	};

	public static void main(String[] args) {
		// closes the socket in case the program ends unexpectedly
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				client.close();
			} catch (IOException ex) {
				System.err.println("Could not close the socket!");
			}
		}));
		// download all the assets if they don't exist
		downloadAssets();

		// prepare the layout and parent window
		GridBagConstraints gridBag = new GridBagConstraints();
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setTitle(Constants.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setup the main canvas and positioning
		panel = new CanvasPanel();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridwidth = tools.length + 2;
		gridBag.gridheight = 8;
		gridBag.fill = GridBagConstraints.BOTH;
		frame.add(panel, gridBag);

		// setup positioning for tool buttons
		gridBag.gridy = 8;
		gridBag.gridwidth = 1;
		gridBag.gridheight = 1;
		gridBag.fill = GridBagConstraints.NONE;

		// add all tool buttons
		for (int i = 0; i < tools.length; i++) {
			JButton button = new JButton();
			button.setText(tools[i]); // set the button label to the tool name
			button.addActionListener(new ToolClickListener(tools[i]));
			button.addKeyListener(panel.key);
			gridBag.gridx = i; // change the x coordinate of the button 
			frame.add(button, gridBag);
		}

		// add one more button for saving
		gridBag.gridx = tools.length;

		JButton saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.addActionListener((ActionEvent evt) -> {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) { // if the user didn't cancel
				File filename = chooser.getSelectedFile();
				try {
					ImageIO.write(panel.canvas, "PNG", filename); // save the current canvas to the selected file
				} catch (IOException ex) {
					System.err.println("IO error (saving image): " + ex.getLocalizedMessage());
				}
			}
		});
		frame.add(saveButton, gridBag);

		// finish initializing the main window
		frame.addKeyListener(panel.key);
		frame.pack();
		frame.setSize(1024, 768);

		// setup the server selection window and show it
		connectFrame = new ConnectFrame("alexhicks.net");
		connectFrame.submitButton.addActionListener((ActionEvent evt) -> { // on button clicked
			String serverName = connectFrame.serverField.getText();
			System.out.println("Attempting to connect to " + serverName + ":" + ServerStart.SERVER_PORT);
			try {
				client = new NetworkClient(serverName, ServerStart.SERVER_PORT); // construct the client with the entered server
				Thread networkThread = new Thread(new ClientDataListener());
				networkThread.start(); // client socket needs to be in a separate thread
			} catch (IOException ex) {
				System.err.println("Could not connect to the server!");
				return;
			}
			connectFrame.dispose(); // hide (and destroy) the connection window
			frame.setVisible(true); // show the main window
		});
		connectFrame.setVisible(true);
	}

	/**
	 * Downloads all assets if they don't already exist.
	 */
	private static void downloadAssets() {
		for (String asset : ASSETS) {
			try {
				downloadAsset(asset);
			} catch (IOException ex) {
				System.err.println("Cannot download asset " + asset);
			}
		}
	}

	/**
	 * Downloads an asset if it does not already exist.
	 *
	 * @param name The name of the asset (no URL or directory should be
	 * associated)
	 * @throws IOException
	 */
	private static void downloadAsset(String name) throws IOException {
		File local = new File(name);
		if (local.exists()) {
			return; // stop if the file already exists
		}
		URL remote = new URL(ASSET_URL + name);
		InputStream in = remote.openStream();
		Files.copy(in, local.toPath(), StandardCopyOption.REPLACE_EXISTING); // overwrite anything with the remote file
	}

	private static class ToolClickListener implements ActionListener {

		private String tool;

		public ToolClickListener(String tool) {
			this.tool = tool;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setTool(this.tool);
			ProtocolStatement stmt = new ProtocolStatement("settool");
			stmt.set("tool", this.tool);
			try {
				client.send(stmt);
			} catch (IOException ex) {
				System.err.println("IO error: " + ex.getLocalizedMessage());
			}
		}

	}

	private static class ClientDataListener implements Runnable {

		@Override
		public void run() {
			while (true) {
				ProtocolStatement stmt = client.receive(); // blocks until received
				if (stmt == null) {
					System.err.println("Bad value received from network!");
					return;
				}
				String positions = stmt.get("positions"); // every message from server to client should have a positions attribute
				String[] tokens = positions.split("###");
				Vector2[] coords = new Vector2[tokens.length];
				for (int i = 0; i < tokens.length; i++) {
					coords[i] = Vector2.fromString(tokens[i]);
				}
				panel.tools.get(stmt.get("tool")).add(coords, stmt.get("drawType").equals("finalShape"), stmt.get("uid")); // set up rendering for the new object
				panel.repaint();
			}
		}
	}
}
