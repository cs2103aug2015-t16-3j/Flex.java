//@@author 

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest4.java checks the validity of a floating task
// and the validity of an occurence task

// format of a floating task
// NOTE: a floating task should not have ";" 
// input
// add <taskname>

// output
// <taskname>

// done
// <taskname> [done]

// format of a recurring task
// input
// add <taskname>; <start>-<end> every <day>; priority

// output 
// add <taskname>, <start>-<end> every <day>, priority

// done
// add <taskname>, <start>-<end> every <day>, priority [done]

public class TestFloatingOutput {
	@Test
	public void testOutput() throws IOException {

		// floating task output
		assertTrue(Checker.isFloatingTaskOutput("task test test1"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a done]"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done]"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done];"));

		// semicolon invalid case
		assertTrue(!Checker.isFloatingTaskOutput("task test test1; a"));

	}

}