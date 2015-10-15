import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest4.java checks the validity of a floating task
// format of a floating task
// task
// e.g.
// task
// NOTE: There should NOT be any ", " (comma and whitespace) Strings in a floating task

public class FlexTest4 {
	@Test
	public void testOutput() throws IOException{

		assertTrue(Checker.isFloatingTask("task"));
		
		assertTrue(Checker.isFloatingTask("task test"));
		
		assertTrue(Checker.isFloatingTask("task test test1"));
		
		assertTrue(Checker.isDoneFloatingTask("task [done]"));
		
		assertTrue(Checker.isDoneFloatingTask("t [done]"));
	}
	
}
