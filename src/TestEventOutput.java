

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (EVENT) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()
// format of an event task:

// output
// <taskname>, <start>-<end> on <date>, <priority>

public class TestEventOutput {

	@Test
	public void testOutput() throws IOException {

		// event task output
		assertTrue(Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior"));

		assertTrue(!Checker.isEventTaskOutput("task is about, 0002-0001 on 1/1/1, priority prior"));

		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior [done]"));

		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior [done"));

		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior done]"));

		// this is a case of invalid partition for the output (GUI Display)
		// String of an event task
		assertTrue(!Checker.isEventTaskOutput("task is about, 0000 0001 on 0/1/1, priority prior"));

	}
}