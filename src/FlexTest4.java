import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest4.java is for testing the validity of a floating task when it is added
// to the .txt schedule file
// i.e. the remaining String after the "add " substring
// title, description, priority
// also tests for the validity of the task string after such an adding input

public class FlexTest4 {
	@Test
	public void testOutput() throws IOException{

		assertTrue(Checker.checkFloatingTaskInput("a, b, c,"));
		assertTrue(Checker.checkFloatingTaskInput("a e , b f , c g "));
		assertTrue(Checker.checkFloatingTaskInput ("title, description, priority"));
		assertTrue(Checker.checkFloatingTaskInput ("title, description, priority,,"));
		
		assertTrue(Checker.checkFloatingTaskOutput("undefined, undefined, undefined, title, description, priority, floating"));
	}
	
}
