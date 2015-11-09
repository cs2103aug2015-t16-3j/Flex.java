<<<<<<< HEAD

=======
//@@author A0131835J
>>>>>>> fa73752610cb84d24203d771bb6708ec2ef706c9

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (EVENT) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()
// format of an event task:

// input
// add <taskname>; <start>-<end> on <date>; <priority>

// output
// <taskname>, <start>-<end> on <date>, <priority>

// done
// <taskname>, <start>-<end> on <date>, <priority> [done]

public class TestEventDoneInput {

	@Test
	public void testOutput() throws IOException {

		// done event task input (user input and/or file storage form)
		assertTrue(Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority [done]"));

		assertTrue(!Checker.isDoneEventTaskInput("task; 0002-0001 on 22/10/1110; priority [done]"));

		assertTrue(!Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority[done]"));

		assertTrue(!Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority [done"));

		assertTrue(!Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority done]"));

		assertTrue(!Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority [done]a"));

		assertTrue(!Checker.isDoneEventTaskInput("t, 0000-0001 1/1/1; priority"));

	}
}