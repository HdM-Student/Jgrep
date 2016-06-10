// Do not format! It will fuck everything up!

package de.se1p04.jgrep;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.se1p04.jgrep.Jgrep;

public class JgrepTest {
	
	// Needed to access command-line-output via "outContent.toString()"
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
	
	/**
	 * Test Jgrep main-Method with one searchterm and one file
	 * 
	 * <p><b>parameters:</b> 'flower ./target/test-classes/input.txt'
	 * 
	 * <p><b>searchterm:</b> 'flower'
	 * <br><b>file:</b>	'./target/test-classes/input.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;Roses are nice flowers.
	 * 		<br>&emsp;Mayflower used to be a ship.
	 */
	@Test
	public void testJgrepMainNoOptionsOneSearchtermLowercaseOneFile() {
		Jgrep.main("flower ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Roses are nice flowers.\nMayflower used to be a ship.\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and one file
	 * 
	 * <p><b>parameters:</b> '-i red ./target/test-classes/input.txt'
	 * 
	 * <p><b>searchterm:</b> 'red'
	 * <br><b>option:</b> '-i'
	 * <br><b>file:</b>	'./target/test-classes/input.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;Red wine is tasty
	 * 		<br>&emsp;The red cross acts worldwide
	 */
	@Test
	public void testJgrepMainIOptionOneSearchtermLowercaseOneFile() {
		Jgrep.main("-i red ./target/test-classes/input.txt".split(" "));
		Assert.assertEquals("Red wine is tasty\nThe red cross acts worldwide\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and two files
	 * 
	 * <p><b>parameters:</b> '-i RED ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterm:</b> 'RED'
	 * <br><b>option:</b> '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt:Red wine is tasty
	 * 		<br>&emsp;./target/test-classes/input.txt:The red cross acts worldwide
	 * 		<br>&emsp;./target/test-classes/secondInput.txt:Errors will show up in red.
	 */
	@Test
	public void testJgrepMainIOptionOneSearchtermUppercaseTwoFiles() {
		Jgrep.main("-i RED ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:Red wine is tasty\n./target/test-classes/input.txt:The red cross acts worldwide\n./target/test-classes/secondInput.txt:Errors will show up in red.\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and two files
	 * 
	 * <p><b>parameters:</b> '-l Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterm:</b> 'Red'
	 * <br><b>option:</b> '-l'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt
	 */
	@Test
	public void testJgrepMainLOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-l Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, two options and two files
	 * 
	 * <p><b>parameters:</b> '-l -i Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterm:</b> 'Red'
	 * <br><b>options:</b> '-l', '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt
	 * 		<br>&emsp;./target/test-classes/secondInput.txt
	 */
	@Test
	public void testJgrepMainLIOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-l -i Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with invalid parameters
	 * 
	 * <p><b>parameters:</b> '-u Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterm:</b> 'Red'
	 * <br><b>option:</b> '-u'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;Invalid option: "-u"! Result without that option:
	 * 		<br>&emsp;./target/test-classes/input.txt:Red wine is tasty
	 */
	@Test
	public void testJgrepMainInvalidOptionOneSearchtermMixcaseTwoFiles() {
		Jgrep.main("-u Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("Invalid option: \"-u\"! Result without that option:\n./target/test-classes/input.txt:Red wine is tasty\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm and a nonexistent file
	 * 
	 * <p><b>parameters:</b> 'test ./this/path/doesNotExist.txt'
	 * 
	 * <p><b>searchterm:</b> 'test'
	 * <br><b>file:</b>	'./this/path/doesNotExist.txt' (nonexistent file)
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;IllegalArgumentException: 'Invalid file path: ./this/path/doesNotExist.txt'
	 */
	@Test
	public void testJgrepMainNoOptionOneSearchtermLowercaseInvalidFile() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("Invalid file path: ./this/path/doesNotExist.txt");
	    Jgrep.main("test ./this/path/doesNotExist.txt".split(" "));
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm (special character) and two files
	 * 
	 * <p><b>parameters:</b> '$ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterm:</b> '$'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/thirdInput.txt:#!\"§$%&/()=?^€
	 * 		<br>&emsp;./target/test-classes/thirdInput.txt:€$¥
	 */
	@Test
	public void testJgrepMainNoOptionOneSearchtermSpecialcharDollarTwoFiles() {
		Jgrep.main("$ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:#!\"§$%&/()=?^€\n./target/test-classes/thirdInput.txt:€$¥\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm (special character) and two files
	 * 
	 * <p><b>parameters:</b> '© ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterm:</b> '©'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/thirdInput.txt:© Linard, Dominik, Timo
	 */
	@Test
	public void testJgrepMainNoOptionOneSearchtermSpecialcharCopyrightTwoFiles() {
		Jgrep.main("© ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:© Linard, Dominik, Timo\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm (special character) and two files
	 * 
	 * <p><b>parameters:</b> '½ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterm:</b> '½'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/thirdInput.txt:½+¼=¾?
	 */
	@Test
	public void testJgrepMainNoOptionOneSearchtermSpecialcharHalfTwoFiles() {
		Jgrep.main("½ ./target/test-classes/input.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/thirdInput.txt:½+¼=¾?\n", outContent.toString());
	}

	/**
	 * Test Jgrep main-Method without searchterm and files
	 * 
	 * <p><b>parameters:</b> '-l -i'
	 * 
	 * <p><b>options:</b> '-l', '-i'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;IllegalArgumentException: 'No key argument'
	 */
	@Test
	public void testJgrepMainLIOptionNoSearchtermNoFile() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("No key argument");
	    Jgrep.main("-l -i".split(" "));
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, two options and two files, but wrong parameter-order
	 * 
	 * <p><b>parameters:</b> 'red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt -l -i'
	 * 
	 * <p><b>searchterm:</b> 'red'
	 * <br><b>options:</b> '-l', '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt
	 * 		<br>&emsp;./target/test-classes/secondInput.txt
	 */
	@Test
	public void testJgrepMainWrongOptionOrderOneSearchtermLowercaseTwoFiles() {
		Jgrep.main("red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt -l -i".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, two options and two files, but wrong parameter-order
	 * 
	 * <p><b>parameters:</b> './target/test-classes/input.txt ./target/test-classes/secondInput.txt -l -i red'
	 * 
	 * <p><b>searchterm:</b> 'red'
	 * <br><b>options:</b> '-l', '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;IllegalArgumentException: 'Invalid file path: red'
	 */
	@Test
	public void testJgrepMainLIOptionOneSearchtermWrongOrderLowercaseTwoFiles() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("Invalid file path: red");
		Jgrep.main("./target/test-classes/input.txt ./target/test-classes/secondInput.txt -l -i red".split(" "));
	}
	
	/**
	 * Test Jgrep main-Method with two searchterms (not allowed!) and two files
	 * 
	 * <p><b>parameters:</b> 'red green ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'red', 'green'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;IllegalArgumentException: 'Invalid file path: green'
	 */
	@Test
	public void testJgrepMainNoOptionTwoSearchtermsLowercaseTwoFiles() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("Invalid file path: green");
		Jgrep.main("red green ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
	}

	/**
	 * Test Jgrep main-Method with one searchterm, five options and two files
	 * 
	 * <p><b>parameters:</b> 'red -i -l -i -l -l ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'red'
	 * <br><b>options:</b> '-i', '-l', '-i', '-l', -l'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt
	 * 		<br>&emsp;./target/test-classes/secondInput.txt
	 */
	@Test
	public void testJgrepMainRepeatOptionsOneSearchtermLowercaseTwoFiles() {
		Jgrep.main("red -i -l -i -l -l ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and four files (duplicates)
	 * 
	 * <p><b>parameters:</b> 'red -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'red'
	 * <br><b>options:</b> '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt:Red wine is tasty
	 * 		<br>&emsp;./target/test-classes/input.txt:The red cross acts worldwide
	 * 		<br>&emsp;./target/test-classes/secondInput.txt:Errors will show up in red.
	 * 		<br>&emsp;./target/test-classes/input.txt:Red wine is tasty
	 * 		<br>&emsp;./target/test-classes/input.txt:The red cross acts worldwide
	 * 		<br>&emsp;./target/test-classes/secondInput.txt:Errors will show up in red.
	 */
	@Test
	public void testJgrepMainIOptionOneSearchtermLowercaseTwoFilesRepeated() {
		Jgrep.main("red -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:Red wine is tasty\n./target/test-classes/input.txt:The red cross acts worldwide\n./target/test-classes/secondInput.txt:Errors will show up in red.\n./target/test-classes/input.txt:Red wine is tasty\n./target/test-classes/input.txt:The red cross acts worldwide\n./target/test-classes/secondInput.txt:Errors will show up in red.\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, two options and four files (duplicates)
	 * 
	 * <p><b>parameters:</b> 'red -i -l ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/input.txt ./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'red'
	 * <br><b>options:</b> '-i -l'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt
	 * 		<br>&emsp;./target/test-classes/secondInput.txt
	 * 		<br>&emsp;./target/test-classes/input.txt
	 * 		<br>&emsp;./target/test-classes/secondInput.txt
	 * 
	 */
	@Test
	public void testJgrepMainILOptionOneSearchtermLowercaseTwoFilesDuplicate() {
		Jgrep.main("red -i -l ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}

	// TODO Export Javadoc
	
	
	// Exercise examples
		/*
		 * TODO Implement special cases that might be problematic
		 * - <Test idea> <(Expected result)>
		 * 
		 * + write comments
		 * - write tests for further methods?: grep(), constructor?
		 * - Run Method with "null"-Parameter -> throws NullPointerException
		 * - write some more tests for fine working examples?
		 * - Write test for different file types.
		 * 
		 * Already implemented:
		 * + Invalid parameters (FAIL: sys.exit -1)
		 * + No search key (FAIL: sys.exit -2)
		 * + Invalid paths (FAIL: sys.exit -3)
		 * + Invalid search keys (? - Are there even invalid search characters?)
		 * + Wrong order of arguments (PASS: Parameter placement shouldn't matter) DISCUSS
		 * + Two search keys (FAIL: Second one should be interpreted as a path -> sys.exit -2)
		 * + Same parameters twice (PASS: Should not matter. Boolean will be set from 'true' to 'true') DISCUSS
		 * 
		 */
}
