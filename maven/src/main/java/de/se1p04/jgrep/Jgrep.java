package de.se1p04.jgrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

// TODO Write & export Javadoc

// TODO Export jar & update installation files (bashrc & jgrep command)


public class Jgrep {
	// Input
	private boolean iO = false;
	private boolean lO = false;
	private String key;
	private final ArrayList<String> targets = new ArrayList<String>();

	// For -l option
//	private final ArrayList<String> files = new ArrayList<String>();

	public static void main(String[] args) {
		new Jgrep(args); // Object will be destroyed immediately
							// after Constructor call.
	}

	// Constructor - prev. init()
	public Jgrep(String[] args) {
		// Sorts parameters
		for (final String arg : args) {
			if (arg.startsWith("-")) {
				switch (arg) {
				case "-i":
					iO = true;
					break;
				case "-l":
					lO = true;
					break;
				default:
					// throw new IllegalArgumentException("Invalid option");
					System.out.println("Invalid <br><b>option:</b> \"" + arg + "\"! Result without that option:");
					break;
				}
			} else if (key == null) {
				key = arg;
			} else {
				targets.add(arg);
			}
		}

		// Stops program if no searchterm was given
		if (key == null) {
			throw new IllegalArgumentException("No key argument");
		}

		// Decides the case
		if (targets.size() == 0) {
			pipeline();
		} else {
			fileInput();
		}
	}

	// Reads out output of previous command
	private void pipeline() {
		String line;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while ((line = br.readLine()) != null) {
				grep(line, "System.in");
			}
			// br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Reads out files
	private void fileInput() {
		fileread: for (final String target : targets) {
			String line;
			try {
				BufferedReader br = new BufferedReader(new FileReader(target));
				while ((line = br.readLine()) != null) {
					Boolean con = grep(line, target);
					if (con) {
						continue fileread;
					}
				}
				br.close();
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid file path: " + target);
				// DISCUSS no print
				// e.printStackTrace();
			}
		}
	}

	// Compares
	private boolean grep(String line, String target) {
		String iLine = line;
		String iKey = key;

		// Pre-output
		// -i option
		if (iO) {
			iLine = iLine.toLowerCase();
			iKey = iKey.toLowerCase();
		}

		// Output
		if (iLine.contains(iKey)) {
			// -l option
			if (lO) {
				System.out.println(target);
				return true;
				
//				if (!files.contains(target)) {
//					files.add(target);
//					System.out.println(target);
//					
//				}
				// No option
			} else {
				if (targets.size() > 1) {
					System.out.print(target + ":");
				}
				System.out.println(line);
				return false;
			}
		}
		return false;
	}

}
