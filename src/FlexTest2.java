import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (EVENT) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()
// format of an event task:
// task, date, starting time, ending time, priority
// e.g.
// task, 1/1/1, 0000, 0001, priority
public class FlexTest2 {

	@Test
	public void testOutput() throws IOException {

		assertTrue(Checker.isEventTask("task, 1/1/1, 0000, 0001, priority"));

		assertTrue(Checker.isEventTask("task, 22/11/1111, 0000, 0001, priority"));

		assertTrue(Checker.isEventTask("task is about, 1/1/1, 0000, 0001, priority prior"));
		
		assertTrue(Checker.isDoneEventTask("task, 1/1/1, 0000, 0001, priority [done]"));
		
		assertTrue(Checker.isDoneEventTask("t, 1/1/1, 0000, 0001, priority [done]"));
	}
}