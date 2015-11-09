//@@author A0131835J

import java.io.File;
import java.io.IOException;

public class FindFilename {
	private static String filename;
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:"
			+ "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\n" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\"
			+ "FlexTest" + "." + "txt";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";

	
	public static boolean find(String input) throws IOException{

		filename = input;
		filename.trim();


		File tempFile = new File(filename);

		if ((!tempFile.exists()) || (filename.length() <= 4)
				|| (!filename.substring(filename.length() - 4, filename.length()).equalsIgnoreCase(".txt"))) {

			FlexWindow.getTextArea().appendText(INVALID_INPUT_MESSAGE + "\n" +
			FILENAME_INPUT_MESSAGE + "\n");
			FlexWindow.getTextArea().appendText("\n");

			System.out.println(INVALID_INPUT_MESSAGE + FILENAME_INPUT_MESSAGE);
			System.out.println();
			
			return false;
		}

		System.out.println();
		System.out.println(FILENAME_ACCEPTED_MESSAGE + PROCEED_MESSAGE);
		System.out.println();

		FlexWindow.getFeedback().appendText(FILENAME_ACCEPTED_MESSAGE + "\n" + 
				PROCEED_MESSAGE + "\n");
		FlexWindow.getTextArea().appendText("\n");
		
		SortAndShow.readAndDisplayAll(input);

		return true;

	}
	
	public static String getFilename(){
		return filename;
	}
}
