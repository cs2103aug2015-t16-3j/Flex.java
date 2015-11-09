import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

public class TestFloatingDoneInput {
	@Test
	public void testOutput() throws IOException {

	// done floating task input(user input command or file storage)
			assertTrue(Checker.isDoneFloatingTaskInput("task [done]"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task[done]"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task [done"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task done]"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task [done]c"));

			// semicolon invalid case
			assertTrue(!Checker.isDoneFloatingTaskInput("task [done];"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));

			assertTrue(!Checker.isDoneFloatingTaskInput("task [don]; a"));

	}

}
