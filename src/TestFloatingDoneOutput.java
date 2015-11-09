import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

public class TestFloatingDoneOutput {
	@Test
	public void testOutput() throws IOException {

			// done floating task output (GUI display)
			assertTrue(Checker.isDoneFloatingTaskOutput("task [done]"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task[done]"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task [done"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task done]"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task [done]d"));

			// semicolon invalid case
			assertTrue(!Checker.isDoneFloatingTaskOutput("task [done];"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));

			assertTrue(!Checker.isDoneFloatingTaskOutput("task [don], a"));
	}

}
