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

public class TestDeadlineOutput {

	@Test
	public void testOutput() throws IOException {

		// deadline task output
		assertTrue(Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015"));
		
		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 [done]"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 done]"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester, bY 2300 on 12/1/2015 [done"));

		assertTrue(!Checker.isDeadlineTaskOutput("task test tester; by 2500 on 12/1/2015"));

	}
}

