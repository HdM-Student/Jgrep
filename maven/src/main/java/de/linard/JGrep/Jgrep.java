package de.linard.JGrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Jgrep {
	// Input
	private boolean iP;
	private boolean lP;
	private String key;
	private final ArrayList<String> targets = new ArrayList<String>();

	// For -l parameter
	private final ArrayList<String> files = new ArrayList<String>();

	public static void main(String[] args) {
		new Jgrep(args); // Object will be destroyed immediately after Constructor call.
	}

	// Constructor
	public Jgrep(String[] args) {
		// Sorts parameters
		for (final String arg : args) {
			switch (arg) {
			case "-i":
				iP = true;
				break;
			case "-l":
				lP = true;
				break;
			default:
				if (key == null) {
					key = arg;
				} else {
					targets.add(arg);
				}
				break;
			}
		}

		// Stops program if no search term was given
		if (key == null) {
			System.out.println("No key argument!");
			System.exit(-1);
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
		for (final String target : targets) {
			String line;
			try {
				BufferedReader br = new BufferedReader(new FileReader(target));
				while ((line = br.readLine()) != null) {
					grep(line, target);
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Compares
	private void grep(String line, String target) {
		String iLine = line;
		String iKey = key;
		
		// Pre-output
		// -i parameter
		if (iP) {
			iLine = iLine.toLowerCase();
			iKey = iKey.toLowerCase();
		}
		
		// Output
		if (iLine.contains(iKey)) {
			// -l parameter
			if (lP) {
				if (!files.contains(target)) {
					files.add(target);
					System.out.println(files);
				}
			// No parameter
			} else {
				if (targets.size() > 1) {
					System.out.print(target + ":");
				}
				System.out.println(line);
			}
		}
	}

}
