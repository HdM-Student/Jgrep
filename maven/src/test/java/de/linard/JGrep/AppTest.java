// Do not format! It will fuck everything up!

package de.linard.JGrep;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppTest {
	
	// Needed to access commandline-output via "outContent.toString()"
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
	
	// Needed to test Exceptions (see: https://github.com/junit-team/junit4/wiki/Exception-testing)
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// Tests
	@Test
	public void testJgrepMainNoOptionsOneSearchtermLowercaseOneFile() {
		Jgrep.main("flower ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Roses are nice flowers.\nMayflower used to be a ship.\n", outContent.toString());
	}
	
	@Test
	public void testJgrepMainIOptionOneSearchtermLowercaseOneFile() {
		Jgrep.main("-i red ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Red wine is tasty\nThe red cross acts worldwide\n", outContent.toString());
	}
	
	@Test
	public void testJgrepMainIOptionOneSearchtermUppercaseTwoFiles() {
		Jgrep.main("-i RED ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:Red wine is tasty\n./target/test-classes/input.txt:The red cross acts worldwide\n./target/test-classes/secondInput.txt:Errors will show up in red.\n", outContent.toString());
	}
	
	@Test
	public void testJgrepMainLOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-l Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n", outContent.toString());
	}
	
	@Test
	public void testJgrepMainLIOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-l -i Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}
	
	/**
	 * 
	 * Test Jgrep main-Method with invalid parameters
	 * parameters: '-u Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * option: '-u'
	 * searchterm: 'Red'
	 * files:	'./target/test-classes/input.txt'
	 * 			'./target/test-classes/secondInput.txt'
	 * 
	 * Expected Output:
	 * 		Invalid option: "-u"! Result without that Option:
	 * 		./target/test-classes/input.txt:Red wine is tasty
	 * 
	 */
	@Test
	public void testJgrepMainInvalidOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-u Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("Invalid option: \"-u\"! Result without that Option:\n./target/test-classes/input.txt:Red wine is tasty\n", outContent.toString());
	}

	@Test
	public void testJgrepMainNoOptionOneSearchtermLowercaseInvalidFile() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage(CoreMatchers.containsString("Invalid file path:"));
	    Jgrep.main("test ./this/path/doesNotExist.txt".split(" "));
	}
	
	@Test
	public void testJgrepMainNoOptionOneSearchtermSpecialcharDollarTwoFiles() {
		Jgrep.main("$ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:#!\"§$%&/()=?^€\n./target/test-classes/thirdInput.txt:€$¥\n", outContent.toString());
	}
	
	@Test
	public void testJgrepMainNoOptionOneSearchtermSpecialcharCopyrightTwoFiles() {
		Jgrep.main("© ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:© Linard, Dominik, Timo\n", outContent.toString());
	}
	
	@Test
	public void test2JgrepMainNoOptionOneSearchtermSpecialcharHalfTwoFiles() {
		Jgrep.main("½ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:½+¼=¾?\n", outContent.toString());
	}
	
	// Exercise examples
		/*
		 * TODO Implement special cases that might be problematic
		 * - <Test idea> <(Expected result)>
		 * 
		 * + Invalid parameters (FAIL: sys.exit -1)
		 * + !!No search key (FAIL: sys.exit -2) DISCUSS: this isn't possible! should not matter?
		 * + Invalid paths (FAIL: sys.exit -3)
		 * + Invalid search keys (? - Are there even invalid search characters?)
		 * - Wrong order of arguments (PASS: Parameter placement shouldn't matter) DISCUSS
		 * - Two search keys (FAIL: Second one should be interpreted as a path -> sys.exit -2)
		 * - Same parameters twice (PASS: Should not matter. Boolean will be set from 'true' to 'true') DISCUSS
		 * - Same paths twiced (PASS: Should not matter. It will simply be searched twice) DISCUSS
		 */
}
