import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest5.java tests if the input for a deadline task works
// date, ending time, title, description, priority
//also tests for the validity of the task string after such an adding input

public class FlexTest5 {

	@Test
	public void testOutput() throws IOException{

		assertTrue(Checker.checkDeadlineTaskInput("1/1/1, 0357, title titles, description, priority pri,"));
		assertTrue(Checker.checkDeadlineTaskInput("1/1/1, 0357, title titles, description, priority pri,,"));
		assertTrue(Checker.checkDeadlineTaskInput("1/1/1, 0357, title titles, description, priority pri"));
		
		assertTrue(Checker.checkDeadlineTaskOutput("1/1/1, undefined, 0357, title titles, description, priority pri, deadline"));
	}
}
