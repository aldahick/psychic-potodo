package net.alexhicks.psychicpotodo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ConnectFrame extends JFrame {

	/**
	 * Text field for the user to input the server name
	 */
	public JTextField serverField;

	/**
	 * Button for submitting
	 */
	public JButton submitButton;

	public ConnectFrame(String defaultServer) {
		// setup layout and frame
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		gridBag.gridheight = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Connect to server");

		// setup server input
		this.serverField = new JTextField();
		this.serverField.setText(defaultServer);
		this.add(this.serverField, gridBag);

		// setup submit button
		gridBag.gridx = 1;
		this.submitButton = new JButton();
		this.submitButton.setText("Connect");
		this.add(this.submitButton, gridBag);

		// finalize layout
		this.pack();
		this.setSize(400, 100);
	}

}
