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


public class FlexTest4 {
	@Test
	public void testOutput() throws IOException{

		// floating task input
		assertTrue(Checker.isFloatingTaskInput("task"));
		
		assertTrue(Checker.isFloatingTaskInput("task, test"));
		
		assertTrue(!Checker.isFloatingTaskInput("; task test"));
		
		assertTrue(!Checker.isFloatingTaskInput(""));
		
		assertTrue(!Checker.isFloatingTaskInput(";task test"));
		
		// floating task output
		assertTrue(Checker.isFloatingTaskOutput("task test test1"));	
		
		assertTrue(Checker.isFloatingTaskOutput("task test test1, a"));
		
		assertTrue(!Checker.isFloatingTaskOutput("task test test1; a"));
	
		// done floating task (user input command or file storage)
		assertTrue(Checker.isDoneFloatingTaskInput("t [done]"));
		
		assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));
		
		assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));
		
		// done floating task (GUI display)
		
		assertTrue(Checker.isDoneFloatingTaskOutput("t [done]"));
		
		assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));
		
		assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));
		
		// recurring task input
		assertTrue(Checker.isRecurringTaskInput("task; 1258-1259 every monday; priority priority1"));
		
		assertTrue(!Checker.isRecurringTaskInput("task; 1258 1259 every monday; priority priority1"));		
		
		// recurring task output
		assertTrue(Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday, priority"));				

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 evry tuesday, priority"));
	
		// done recurring task (user input command or file storage)
	    assertTrue(Checker.isDoneRecurringTaskInput("tasker; 0001-2359 every wednesday; priority [done]"));		

	    assertTrue(!Checker.isDoneRecurringTaskInput("tasker; 0001-2359 every wednesday, priority"));	
		
		// done recurring task (GUI display)
	    assertTrue(Checker.isDoneRecurringTaskOutput("tasker, 0001-2359 every wednesday, priority [done]"));		

	    assertTrue(!Checker.isDoneRecurringTaskOutput("tasker; 0001-2359 every wednesday; priority"));		

	}
	
}
