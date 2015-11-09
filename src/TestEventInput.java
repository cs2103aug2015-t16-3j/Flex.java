//@@author

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (EVENT) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()
// format of an event task:

// input
// add <taskname>; <start>-<end> on <date>; <priority>

public class TestEventInput {

	@Test
	public void testOutput() throws IOException {

		// event task input
		assertTrue(Checker.isEventTaskInput("task; 0000-0001 on 1/1/1; priority"));

		assertTrue(Checker.isEventTaskInput("task; 1201-1301 on 2/12/2015; priority"));
		
		assertTrue(Checker.isEventTaskInput("task; 1201-1301 on 3/12/2015; priority"));
		
		assertTrue(!Checker.isEventTaskInput("task; 0002-0001 on 1/1/1; priority"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-0001 on 22/11/1111; priority [done]"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority [done"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority done]"));

	
	}
}