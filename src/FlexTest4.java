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

		// done floating task input(user input command or file storage)
		assertTrue(Checker.isDoneFloatingTaskInput("t [done]"));

		assertTrue(!Checker.isDoneFloatingTaskInput("t[done]"));

		assertTrue(!Checker.isDoneFloatingTaskInput("t [done"));

		assertTrue(!Checker.isDoneFloatingTaskInput("t done]"));

		assertTrue(!Checker.isDoneFloatingTaskInput("t [done]c"));

		// semicolon invalid case
		assertTrue(!Checker.isDoneFloatingTaskInput("t [done];"));

		assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));

		assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));

		// done floating task output (GUI display)

		assertTrue(Checker.isDoneFloatingTaskOutput("t [done]"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("t[done]"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("t [done"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("t done]"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("t [done]d"));

		// semicolon invalid case
		assertTrue(!Checker.isDoneFloatingTaskOutput("task [done];"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));

		assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));

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

		// recurring task output
		assertTrue(Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday"));

		assertTrue(Checker.isRecurringTaskOutput("task name, 0002-0003 every tuesday"));

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
