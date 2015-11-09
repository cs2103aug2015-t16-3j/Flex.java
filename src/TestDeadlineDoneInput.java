<<<<<<< HEAD
//@@author 
=======
<<<<<<< HEAD

=======
//@@author A0131835J
>>>>>>> fa73752610cb84d24203d771bb6708ec2ef706c9
>>>>>>> 10ac6224e03db5f900e19b243c5669f2b421ebb9

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// format of a deadline task

// input 
// add <taskname>; by <end> on <date>

// done
// <taskname>, by <end> on <date>

public class TestDeadlineDoneInput {

	@Test
	public void testOutput() throws IOException {

		// done deadline task (user input and/or file storage form)
		assertTrue(Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1 [done]"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1[done]"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("task; by 2000 on 1/1/1 [done]a"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("t; by 2000 on 1/1/1 [done"));

		assertTrue(!Checker.isDoneDeadlineTaskInput("t, by 2000 on 1/1/1 [done"));

	}
}
