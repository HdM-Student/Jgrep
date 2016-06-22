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

/**
 * Junit test for Jgrep class
 * 
 * @author Linard Hug, Timo Sutterer, Dominik Stoll
 * @version 1.0
 */
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
	 * 		<br>&emsp;IllegalArgumentException: 'jgrep: invalid option -- 'u'. Usage: jgrep [OPTION]... PATTERN [FILE]...'
	 */
	@Test
	public void testJgrepMainInvalidOptionOneSearchtermMixcaseTwoFiles() {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("jgrep: invalid option -- 'u'\nUsage: jgrep [OPTION]... PATTERN [FILE]...");
	    Jgrep.main("-u Red ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
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
	    thrown.expectMessage("jgrep: ./this/path/doesNotExist.txt: No such file or directory");
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
	 * 		<br>&emsp;./target/test-classes/thirdInput.txt:#!\"§$%&amp;/()=?^€
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
	public void testJgrepMainNoOptionsOneSearchtermSpecialcharHalfTwoFiles() {
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
	 * 		<br>&emsp;IllegalArgumentException: 'Usage: jgrep [OPTION]... PATTERN [FILE]...'
	 */
	@Test
	public void testJgrepMainLIOptionsNoSearchtermNoFile() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("Usage: jgrep [OPTION]... PATTERN [FILE]...");
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
	 * 		<br>&emsp;IllegalArgumentException: 'jgrep: red: No such file or directory'
	 */
	@Test
	public void testJgrepMainLIOptionsOneSearchtermWrongOrderLowercaseTwoFiles() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("jgrep: red: No such file or directory");
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
	public void testJgrepMainNoOptionsTwoSearchtermsLowercaseTwoFiles() throws IllegalArgumentException {
	    thrown.expect(IllegalArgumentException.class);
	    thrown.expectMessage("jgrep: green: No such file or directory");
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
	public void testJgrepMainILOptionsOneSearchtermLowercaseTwoFilesDuplicate() {
		Jgrep.main("red -i -l ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/input.txt ./target/test-classes/secondInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n./target/test-classes/input.txt\n./target/test-classes/secondInput.txt\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and three files
	 * 
	 * <p><b>parameters:</b> 'error -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'red'
	 * <br><b>option:</b> '-i'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/secondInput.txt:Errors will show up in red.
	 * 
	 */
	@Test
	public void testJgrepMainIOptionOneSearchtermLowercaseThreeFiles() {
		Jgrep.main("error -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/secondInput.txt:Errors will show up in red.\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm and three files
	 * 
	 * <p><b>parameters:</b> 'worldwide ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'worldwide'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/secondInput.txt:Errors will show up in red.
	 * 
	 */
	@Test
	public void testJgrepMainNoOptionsOneSearchtermLowercaseThreeFiles() {
		Jgrep.main("worldwide ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:The red cross acts worldwide\n", outContent.toString());
	}
	
	/**
	 * Test Jgrep main-Method with one searchterm, one option and three files
	 * 
	 * <p><b>parameters:</b> 'fLoWerS -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>searchterms:</b> 'fLoWerS'
	 * <br><b>files:</b>
	 * 		<br>&emsp;'./target/test-classes/input.txt'
	 * 		<br>&emsp;'./target/test-classes/secondInput.txt'
	 * 		<br>&emsp;'./target/test-classes/thirdInput.txt'
	 * 
	 * <p><b>expected output:</b>
	 * 		<br>&emsp;./target/test-classes/input.txt:Roses are nice flowers.
	 * 
	 */
	@Test
	public void testJgrepMainIOptionOneSearchtermMixcaseThreeFiles() {
		Jgrep.main("fLoWerS -i ./target/test-classes/input.txt ./target/test-classes/secondInput.txt ./target/test-classes/thirdInput.txt".split(" "));
		Assert.assertEquals("./target/test-classes/input.txt:Roses are nice flowers.\n", outContent.toString());
	}
}
