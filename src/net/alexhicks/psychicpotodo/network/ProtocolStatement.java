package net.alexhicks.psychicpotodo.network;

import java.util.HashMap;
import java.util.Objects;

/**
 * An abstracted way of building a standardized network statement adhering to
 * the protocol
 *
 * @author Alex
 */
public class ProtocolStatement {

	private String action;
	private HashMap<String, String> arguments;

	public ProtocolStatement(String action) {
		this.action = action;
		this.arguments = new HashMap<>();
	}

	public String get(String name) {
		return this.arguments.get(name);
	}

	public void set(String name, String value) {
		this.arguments.put(name, value.replace(" ", ""));
	}

	public void set(String name, double value) {
		this.set(name, Double.toString(value));
	}

	public void set(String name, float value) {
		this.set(name, Float.toString(value));
	}

	public void set(String name, int value) {
		this.set(name, Integer.toString(value));
	}
	
	public void set(String name, long value) {
		this.set(name, Long.toString(value));
	}

	public void set(String name, Object[] values) {
		String[] strValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			strValues[i] = values[i].toString();
		}
		String data = String.join("###", strValues).replace(" ", "");
		this.set(name, data);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProtocolStatement)) {
			return false;
		}
		return this.hashCode() == ((ProtocolStatement) other).hashCode();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.action);
		hash = 53 * hash + Objects.hashCode(this.arguments);
		return hash;
	}

	@Override
	public String toString() {
		String data = this.action + " ";
		for (String name : this.arguments.keySet()) {
			data += name + "=" + this.arguments.get(name) + " ";
		}
		return data.substring(0, data.length() - 1);
	}

	public String getAction() {
		return this.action;
	}

	public static ProtocolStatement fromNetworkString(String raw) {
		if (raw.isEmpty()) {
			return null;
		}
		String[] args = raw.split(" ");
		ProtocolStatement stmt = new ProtocolStatement(args[0]);
		if (args.length == 1) {
			return stmt;
		}
		for (int i = 1; i < args.length; i++) {
			String[] tokens = args[i].split("=");
			if (tokens.length <= 1) {
				continue;
			}
			stmt.set(tokens[0], tokens[1]);
		}
		return stmt;
	}
}
