import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest1.java tests that valid dates in the required format are true, using valid date Strings, to test the 
// logic of checkDate()

public class FlexTest1 {

	@Test
	public void testOutput() throws IOException {

		assertTrue(Checker.isValidDate("31/12/2012"));

		// this is a case in the valid
		// date partition,
		// provided that it is a leap year.
		assertTrue(Checker.isValidDate("29/2/2012"));

		assertTrue(Checker.isValidDate("28/2/2012"));

		assertTrue(Checker.isValidDate("31/1/2012"));

		assertTrue(Checker.isValidDate("1/1/2012"));

		assertTrue(Checker.isValidDate("1/1/2011"));
		
		// the following are in the invalid partitions for a valid date
		assertTrue(!Checker.isValidDate("0/1/2012"));
		
		assertTrue(!Checker.isValidDate("1/-1/2012"));
		
		assertTrue(!Checker.isValidDate("32/1/2012"));
		
		assertTrue(!Checker.isValidDate("1/13/2012"));

	}
}