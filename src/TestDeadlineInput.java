<<<<<<< HEAD

=======
//@@author A0131835J
>>>>>>> fa73752610cb84d24203d771bb6708ec2ef706c9

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// format of a deadline task

// input 
// add <taskname>; by <end> on <date>

// done
// <taskname>, by <end> on <date>

public class TestDeadlineInput {

	@Test
	public void testOutput() throws IOException {

		// deadline task input
		assertTrue(Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 [done]"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 done]"));

		assertTrue(!Checker.isDeadlineTaskInput("task; by 2000 on 1/1/1 [done"));

		assertTrue(!Checker.isDeadlineTaskInput("task test testing, by 2359 On 12/12/2015"));

	}
}
