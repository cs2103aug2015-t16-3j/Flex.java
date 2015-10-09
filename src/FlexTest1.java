import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest1.java tests that valid dates in the required format are true, using valid date Strings, to test the 
// logic of checkDate()

public class FlexTest1{

	
	@Test
	public void testOutput() throws IOException{
		Checker tester = new Checker();
		FlexWindow flexWindow = new FlexWindow();
		
		assertTrue(tester.checkDate("31/12/2012", flexWindow));
		
		assertTrue(tester.checkDate("29/2/2012", flexWindow));
		
		assertTrue(tester.checkDate("28/2/2012", flexWindow));
		
		assertTrue(tester.checkDate("31/1/2012", flexWindow));
		
		assertTrue(tester.checkDate("1/1/2012", flexWindow));
		
		assertTrue(tester.checkDate("1/1/2011", flexWindow));
		
	}
}