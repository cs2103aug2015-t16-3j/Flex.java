//@@author A0131835J

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// format of a deadline task

// output 
// <taskname>, by <end> on <date>

// done
// <taskname>, by <end> on <date>

public class TestDeadlineDoneOutput {

	@Test
	public void testOutput() throws IOException {

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
