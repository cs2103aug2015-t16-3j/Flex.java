import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// format of a deadline task

// input 
// add <taskname>; by <end> on <date>

// output 
// <taskname>, by <end> on <date>

// done
// <taskname>, by <end> on <date>

public class FlexTest5 {

	@Test
	public void testOutput() throws IOException {

		// deadline task input
		assertTrue(Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 [done]"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 done]"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 [done"));

		assertTrue(!Checker.isDeadlineTaskInput("task test testing, by 2359 On 12/12/2015"));

		// deadline task output
		assertTrue(Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015"));
		
		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 [done]"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 done]"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 [done"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester; by 2500 on 12/1/2015"));

		// done deadline task (user input and/or file storage form)
		assertTrue(Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1 [done]"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1[done]"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1 [done]a"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("t; by 2000 on 1/1/1 [done"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("t, by 2000 on 1/1/1 [done"));

		// done deadline task output (GUI Display)

		assertTrue(Checker.isDoneDeadlineTaskOutput("task, by 2000 on 1/1/1 [done]"));

		assertTrue(!Checker.isDoneDeadlineTaskOutput("task, by 2000 on 1/1/1[done]"));

		assertTrue(!Checker.isDoneDeadlineTaskOutput("task, by 2000 on 1/1/1 [done]a"));

		assertTrue(!Checker.isDoneDeadlineTaskOutput("t, by 2000 on 1/1/1 [done"));

		assertTrue(!Checker.isDoneDeadlineTaskOutput("t, by 2000 on 1/1/1 [done"));

		// all Strings, without the "[done]" substring, are in
		// the invalid input partition for a deadline task which is done
		// (other than a null String)
		assertTrue(!Checker.isDoneDeadlineTaskOutput("t, by 2000 on 1/1/1 [done"));

	}
}
