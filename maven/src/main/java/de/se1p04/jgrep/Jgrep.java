package de.se1p04.jgrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * <p>Searches through given files line per line for a keyword.
 * <br>It outputs the lines that contain said keyword.
 * 
 * <p><b>Available options</b>
 * <br><b>-i</b>: case insensitive searches
 * <br><b>-l</b>: shows filenames containing matches instead
 * 
 * <p><b>Usage:</b>
 * <br>jgrep [&lt;option&gt;]... &lt;search term&gt; [&lt;file path&gt;]...
 * 
 * @author Linard Hug, Timo Sutterer, Dominik Stoll
 * @version 1.0
 */
public class Jgrep {
	
	private boolean iO = false;
	private boolean lO = false;
	private String key;
	private final ArrayList<String> targets = new ArrayList<String>();

	/**
	 * Creates a Jgrep object and redirects the parameter to the constructor.
	 * 
	 * @param args parameters entered by the user
	 */
	public static void main(String[] args) throws IllegalArgumentException {
		new Jgrep(args); // Object will be destroyed immediately
							// after Constructor call.
	}

	/**
	 * Initializes the search.
	 * 
	 * <p>Sorts parameter args into options, search term and file paths.
	 * <p>Checks if parameter "-i" is given for case insensitive searches
	 * <br>Checks if parameter "-l": is given for just showing the filenames
	 * 
	 * <p>Decides the case:
	 * <br>&emsp;No file path: reads out of pipeline
	 * <br>&emsp;Valid file path: reads out file
	 * 

	 * 
	 * @param args parameters entered by the user
	 * 
	 * @throws IllegalArgumentException If a wrong option was given
	 * @throws IllegalArgumentException If no search term was given
	 * @throws IllegalArgumentException If invalid file path was given
	 */
	public Jgrep(String[] args) throws IllegalArgumentException {
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
					throw new IllegalArgumentException("jgrep: invalid option -- '" + arg.substring(1) + "'" + "\nUsage: jgrep [OPTION]... PATTERN [FILE]...");
				}
			} else if (key == null) {
				key = arg;
			} else {
				targets.add(arg);
			}
		}

		// Stops program if no search term was given
		if (key == null) {
			throw new IllegalArgumentException("Usage: jgrep [OPTION]... PATTERN [FILE]...");
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
				grep(line, "(Standard Input)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Reads out files
	private void fileInput() throws IllegalArgumentException {
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
				throw new IllegalArgumentException("jgrep: " + target + ": No such file or directory");
			}
		}
	}

	// Compares
	private boolean grep(String line, String target) {
		String iLine = line;
		String iKey = key;

		/* Before output: Checking on -i option
		 * If -i option was given, line and key are put to lower case to search case insensitive
		*/
		if (iO) {
			iLine = iLine.toLowerCase();
			iKey = iKey.toLowerCase();
		}

		// Output: Checking if the line contains the search term
		if (iLine.contains(iKey)) {
			/* Checking on -l option
			 * If -l option was given, only the filenames are output
			 */
			if (lO) {
				System.out.println(target);
				return true;
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
