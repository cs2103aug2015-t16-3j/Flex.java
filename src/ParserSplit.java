
//@@author A0131830U
//ParserSplit is to parser the input command, find the command type and the arguments.
import java.util.*;

public class ParserSplit {
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_CHANGE = "change";
	private static final String COMMAND_MARK = "mark";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_SHOW = "show";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_CLEAR = "clear";

	public static Command parse(String userInput) {
		Command command;
		ArrayList<String> parameters = splitString(userInput);
		String commandType = getCommandType(parameters);
		String arguments = getArguments(parameters);
		switch (commandType.toLowerCase()) {
		case COMMAND_ADD:
			command = addCommand(arguments);
			break;
		case COMMAND_DELETE:
			command = deleteCommand(arguments);
			break;
		case COMMAND_CHANGE:
			command = changeCommand(arguments);
			break;
		case COMMAND_UNDO:
			command = undoCommand();
			break;
		case COMMAND_SHOW:
			command = showCommand(arguments);
			break;
		case COMMAND_MARK:
			command = markCommand(arguments);
			break;
		case COMMAND_SEARCH:
			command = searchCommand(arguments);
			break;
		case COMMAND_CLEAR:
			command = clearCommand();
			break;
		case COMMAND_EXIT:
			command = exitCommand();
			break;
		default:
			command = invalidCommand();
		}
		return command;
	}

	// split the input command into two parts
	private static ArrayList<String> splitString(String userInput) {
		String[] temp = userInput.trim().split(" ", 2);
		return new ArrayList<String>(Arrays.asList(temp));
	}

	// return the command type
	private static String getCommandType(ArrayList<String> parameters) {
		return parameters.get(0);
	}

	// return the arguments
	private static String getArguments(ArrayList<String> parameters) {
		String nulltemp = "";
		if (parameters.size() < 2) {
			return nulltemp;
		}
		String temp = parameters.get(1);
		return temp;
	}

	// add command type
	private static Command addCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}
		Command command = new Command(Command.Type.ADD);
		return command;
	}

	// delete command type
	private static Command deleteCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}
		Command command = new Command(Command.Type.DELETE);
		return command;
	}

	// change command type
	private static Command changeCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}
		Command command = new Command(Command.Type.CHANGE);

		return command;

	}

	// mark task as done or not done
	private static Command markCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}
		Command command = new Command(Command.Type.MARK);

		return command;
	}

	// search command type
	private static Command searchCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}

		Command command = new Command(Command.Type.SEARCH);

		return command;
	}

	// show command type
	private static Command showCommand(String arguments) {
		if (arguments.trim().length() == 0) {
			Command command = new Command(Command.Type.INVALID);
			return command;
		}
		Command command = new Command(Command.Type.SHOW);

		return command;
	}

	// undo command type
	private static Command undoCommand() {
		Command command = new Command(Command.Type.UNDO);
		return command;
	}

	private static Command clearCommand() {
		Command command = new Command(Command.Type.CLEAR);
		return command;
	}

	// exit command type
	private static Command exitCommand() {
		Command command = new Command(Command.Type.EXIT);
		return command;
	}

	// invalid command type
	private static Command invalidCommand() {
		Command command = new Command(Command.Type.INVALID);
		return command;
	}
}
