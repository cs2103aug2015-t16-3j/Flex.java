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


public class FlexTest2 {

	@Test
	public void testOutput() throws IOException {
		
		// event task input
		assertTrue(Checker.isEventTaskInput("task; 0000-0001 on 1/1/1; priority"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-0001 on 22/11/1111; priority [done]"));

		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority"));
		
		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority [done"));
		
		assertTrue(!Checker.isEventTaskInput("task; 0000-2400 on 22/11/1111; priority done]"));
		
		// event task output
		assertTrue(Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior"));
		
		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior [done]"));
		
		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior [done"));
		
		assertTrue(!Checker.isEventTaskOutput("task is about, 0000-0001 on 1/1/1, priority prior done]"));
		
		// this is a case of invalid partition for the output (GUI Display) String of an event task
		assertTrue(!Checker.isEventTaskOutput("task is about, 0000 0001 on 0/1/1, priority prior"));
		
		// done event task input (user input and/or file storage form)
		assertTrue(Checker.isDoneEventTaskInput("task; 0000-0001 on 22/10/1110; priority [done]"));
		
		assertTrue(!Checker.isDoneEventTaskInput("t, 0000-0001 1/1/1; priority"));
		
		// done event task output(GUI display)
		assertTrue(Checker.isDoneEventTaskOutput("task, 0000-0001 on 22/10/1110, priority [done]"));
		
		assertTrue(!Checker.isDoneEventTaskOutput("t, 0000-0001 1/1/1, priority"));
	}
}