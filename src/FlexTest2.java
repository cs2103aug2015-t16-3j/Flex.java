import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest2.java tests that valid USUAL (NORMAL) Task "lines" are true, using valid Task "lines", to test the logic of checkTask()

public class FlexTest2{

	
	@Test
	public void testOutput() throws IOException{

		assertTrue(Checker.checkTask("8/8/2013, 0001, 0002, test1minuTe, test1minute only, -100, blocked"));
		
		assertTrue(Checker.checkTask("27/1/2014, 1000, 1100, title hundred, description hundred, hundred, blocked"));
		
		assertTrue(Checker.checkTask("31/12/2016, 0001, 0003, title, desc, pri, ca"));
		
		assertTrue(Checker.checkTask("1/2/2015, 0800, 0801, title four, description five, two, pending"));
		
		assertTrue(Checker.checkTask("1/3/2015, 1000, 1150, title three, description eleven, 2, non-default category"));
		
		assertTrue(Checker.checkTask("11/12/2014, 1000, 1100, title hundred, description1 234, 1, blocked"));
		
	}
}