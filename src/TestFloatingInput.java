//@@author A0131830U

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest4.java checks the validity of a floating task
// and the validity of an occurence task

// format of a floating task
// NOTE: a floating task should not have ";" 
// input
// add <taskname>

public class TestFloatingInput {
	@Test
	public void testOutput() throws IOException {

		// floating task input
		assertTrue(Checker.isFloatingTaskInput("task"));

		assertTrue(!Checker.isFloatingTaskInput("task, test [done]"));

		assertTrue(!Checker.isFloatingTaskInput("task, test done]"));

		assertTrue(!Checker.isFloatingTaskInput("task, test [done"));

		// semicolon invalid case
		assertTrue(!Checker.isFloatingTaskInput("; task test"));

		assertTrue(!Checker.isFloatingTaskInput(""));

		assertTrue(!Checker.isFloatingTaskInput(";task test"));

	}

}