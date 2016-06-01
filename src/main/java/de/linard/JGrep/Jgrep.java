package de.linard.JGrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Jgrep {
	// Input
	private static final ArrayList<String> param = new ArrayList<String>();
	private static String key;
	private static final ArrayList<String> targets = new ArrayList<String>();
	
	// Output
	private static final ArrayList<String> files = new ArrayList<String>();

	public static void main(String[] args) {
		

		init(args);
	}

	private static void init(String[] args) {
		
		// Sorts parameters
				for (final String arg : args) {
					if (arg.startsWith("-")) {
						param.add(arg);
					} else if (key == null) {
						key = arg;
					} else {
						targets.add(arg);
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
		
		// Outputs file names for -l parameter
		for (final String file : files) {
			System.out.println(file);
		}
	}

	// Reads out output of previous command
	private static void pipeline() {
		String line;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while ((line = br.readLine()) != null) {
				grep(line, "System.in");
			}
			//br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Reads out files
	private static void fileInput() {
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
	private static void grep(String line, String target) {
		String ILine = line;
		String IKey = key;

		// -i parameter
		if (param.contains("-i")) {
			ILine = ILine.toLowerCase();
			IKey = IKey.toLowerCase();
		}

		if (ILine.contains(IKey)) {
			// -l parameter
			if (param.contains("-l")) {
				if (!files.contains(target)) {
					files.add(target);
					// files will be outputted at the end of init() method
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
