import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (NORMAL) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()
// format of a normal task:
// task, date, starting time, ending time, priority
// e.g.
// task, 1/1/1, 0000, 0001, priority
public class FlexTest2{

	
	@Test
	public void testOutput() throws IOException{

		assertTrue(Checker.isNormalTask("task, 1/1/1, 0000, 0001, priority"));
		
		assertTrue(Checker.isNormalTask("task, 22/11/1111, 0000, 0001, priority"));
		
		assertTrue(Checker.isNormalTask("task is about, 1/1/1, 0000, 0001, priority prior"));
	}
}