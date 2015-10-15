import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// format of a deadline task
// task, date, ending time
// e.g.
// task, 1/1/1, 0000

public class FlexTest5 {

	@Test
	public void testOutput() throws IOException {

		assertTrue(Checker.isDeadlineTask("task, 1/1/1, 2000"));

		assertTrue(Checker.isDeadlineTask("task test testing, 12/12/2015, 2359"));

		assertTrue(Checker.isDeadlineTask("task test tester, 12/1/2015, 2300"));
		
		assertTrue(Checker.isDoneDeadlineTask("task, 1/1/1, 2000 [done]"));
		
		assertTrue(Checker.isDoneDeadlineTask("t, 1/1/1, 2000 [done]"));

	}
}
