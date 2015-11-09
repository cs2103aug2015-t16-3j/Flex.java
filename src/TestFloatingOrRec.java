

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

public class TestFloatingOrRec {
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

		// floating task output
		assertTrue(Checker.isFloatingTaskOutput("task test test1"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a done]"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done]"));

		assertTrue(!Checker.isFloatingTaskOutput("task test test1, a [done];"));

		// semicolon invalid case
		assertTrue(!Checker.isFloatingTaskOutput("task test test1; a"));


		// recurring task input
		assertTrue(Checker.isRecurringTaskInput("task; 1258-1259 every monday"));

		assertTrue(Checker.isRecurringTaskInput("task; 1258-1259 every monday "));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday a"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday1"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday ; "));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday,"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every mondayds"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1358-1259 every monday; priority priority1"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday; priority priority1 [done]"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday; priority priority1 [done"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258-1259 every monday; priority priority1 done]"));

		assertTrue(!Checker.isRecurringTaskInput("task; 1258 1259 every monday; priority priority1"));
		
		// the following serve as boundary cases the invalid partition for SortAndShow.isValidTime(), which is called in isRecurringTaskInput (only the time fields are changed)
		assertTrue(!Checker.isRecurringTaskInput("task; 00000-1259 every monday"));
		
		assertTrue(!Checker.isRecurringTaskInput("task; 1258-2400 every monday"));
		
		assertTrue(!Checker.isRecurringTaskInput("task; 1258-02359 every monday"));

		// recurring task output
		assertTrue(Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday"));

		assertTrue(Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday "));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday ,"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday;"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday a"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday1"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0102-0003 every tuesday, priority"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday, priority [done]"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday, priority [done"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday, priority done]"));

		assertTrue(!Checker.isRecurringTaskOutput("task name, 0002-0003 evry tuesday, priority"));

	}

}