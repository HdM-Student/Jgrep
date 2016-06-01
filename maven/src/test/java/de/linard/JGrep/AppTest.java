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
    	String[] args = {"flower", "../../../../../test/java/de/linard/JGrep/resources/input.txt"};
        new Jgrep(args);
    }
}
