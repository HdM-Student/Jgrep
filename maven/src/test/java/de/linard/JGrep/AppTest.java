package de.linard.JGrep;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Dummy test method
	 */
	@Test
	public void testApp() {
		new Jgrep("flower ./target/test-classes/input.txt".split(" "));
		new Jgrep("-i red ./target/test-classes/input.txt".split(" "));
		new Jgrep("-i RED ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		new Jgrep("-l Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		new Jgrep("-l -i Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
	}
}
