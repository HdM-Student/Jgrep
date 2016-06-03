// Do not format! It will fuck everything up!

package de.linard.JGrep;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		outContent.reset();
	}

	@Test
	public void testApp() {
		// Exercise examples
		/*
		 * FIXME Junit test fails for expected. Output should be the same with \n and gets displayed as such,
		 * but somehow it's not recognized as being equal.
		 * Even one-line output fails because of the new line println creates.
		 * 
		 * Test only passes with not '\n' in the expected value and if System.out.print is used in Jgrep
		 */
		Jgrep.main("flower ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Roses are nice flowers.\nMayflower used to be a ship.\n", outContent.toString());

		Jgrep.main("-i red ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Red wine is tasty\nThe red cross acts worldwide\n", outContent.toString());

		Jgrep.main("-i RED ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:Red wine is tasty\n./target/test-classes/input.txt:The red cross acts worldwide\n./target/test-classes/secondInput.txt:Errors will show up in red.\n", outContent.toString());

		Jgrep.main("-l Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt", outContent.toString());

		Jgrep.main("-l -i Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());

		/*
		 * TODO Implement special cases that might be problematic
		 * - <Test idea> <(Expected result)>
		 * 
		 * - Invalid parameters (FAIL: sys.exit -1)
		 * - No search key (FAIL: sys.exit -2)
		 * - Invalid paths (FAIL: sys.exit -3)
		 * - Invalid search keys (? - Are there even invalid search characters?)
		 * - Wrong order of arguments (PASS: Parameter placement shouldn't matter) DISCUSS
		 * - Two search keys (FAIL: Second one should be interpreted as a path -> sys.exit -2)
		 * - Same parameters twice (PASS: Should not matter. Boolean will be set from 'true' to 'true') DISCUSS
		 * - Same paths twice (PASS: Should not matter. It will simply be searched twice) DISCUSS
		 */
	}
}
