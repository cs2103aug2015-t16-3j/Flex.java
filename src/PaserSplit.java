import java.util.*;
public class PaserSplit {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_CHANGE = "change";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_SHOW = "show";
    private static final String COMMAND_DISPLAY = "display";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_EXIT = "exit";
    
    public PaserSplit(){
    }
    public Command parse(String userInput){
    	Command command;
    	ArrayList<String> parameters = splitString(userInput);
    	String commandType = getCommandType(parameters);
    	String arguments = getArguments(parameters);
    	switch(commandType.toLowerCase()){
	      case COMMAND_ADD : command = addCommand(arguments);
	                         break;
	      case COMMAND_DELETE: command = deleteCommand(arguments);
	                           break;
	      case COMMAND_CHANGE: command = changeCommand(arguments);
    	                       break;
	      case COMMAND_UNDO: command = undoCommand();
	                         break;
	      case COMMAND_SHOW: command = displayCommand(arguments);
	                         break;
	      case COMMAND_DISPLAY: command = displayCommand(arguments);
	                         break;
	      case COMMAND_SEARCH: command = searchCommand(arguments);
	                         break;
	      case COMMAND_EXIT: command = ecitCommand();
	                         break;
	      default: command = invalidCommand();
    	}
    	return command;
    	} 
    public ArrayList<String> splitString(String userInput){
    	String[] temp = userInput.trim().split(" ", 2);
    	return new ArrayList<String>(Arrays.asList(temp));
    }
    public String getCommandType(ArrayList<String> parameters){
    	return parameters.get(0);
    }
    public String getArguments(ArrayList<String> parameters){
    	String temp = parameters.get(1);
    	return temp;
        }
    
    public Command addCommand(String arguments){
    	Command command = new Command(Command.Type.ADD);
    	if(arguments.contains("by")&&arguments.contains("on")){
    		command.setTaskType("Deadline");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String endTime = splitSec[1];
    		String date = splitSec[3];
    		command.setTaskName(taskName);
    		command.setEndTime(endTime);
    		command.setDate(date);
    		return command;
    	}
    	else if(arguments.contains("on")){
    		command.setTaskType("Event");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		String priority = splitFirst[2];
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String date = splitSec[2];
    		String[] splitThird = splitSec[0].trim().split("-");
    		String startTime = splitThird[0];
    		String endTime = splitThird[1];
    		command.setTaskName(taskName);
    		command.setStartTime(startTime);
    		command.setEndTime(endTime);
    		command.setDate(date);
    		command.setPriority(priority);
    		return command;
    	}
    	else if(arguments.contains("every")){
    		command.setTaskType("Recurring");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		String priority = splitFirst[2];
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String day = splitSec[2];
    		String[] splitThird = splitSec[0].trim().split("-");
    		String startTime = splitThird[0];
    		String endTime = splitThird[1];
    		command.setTaskName(taskName);
    		command.setStartTime(startTime);
    		command.setEndTime(endTime);
    		command.setDay(day);
    		command.setPriority(priority);
    		return command;
    	}
    	else{
    		command.setTaskType("floating");
    		String taskName = arguments.trim();
    		command.setTaskName(taskName);
    		return command;
    	}
    }
    public Command deleteCommand(String arguments){
    	Command command = new Command(Command.Type.DELETE);
    	if(arguments.contains("floating")){
    		command.setTaskType("floating");
    		String[] splitFirst = arguments.split(" ");
    		int number = Integer.valueOf(splitFirst[1]);
    		command.setNumber(number);
    		return command;
    	}
    	else if(arguments.contains("rec")){
    		command.setTaskType("Recurring");
    		String[] splitFirst = arguments.split(" ");
    		int number = Integer.valueOf(splitFirst[1]);
    		command.setNumber(number);
    		return command;
    	}
    	else{
    		command.setTaskType("Event");
    		String[] splitFirst = arguments.split(" ");
    		String date = splitFirst[0];
    		int number = Integer.valueOf(splitFirst[1]);
    		command.setDate(date);
    		command.setNumber(number);
    		return command;
    	}
    	
    }
    public Command undoCommand(){
    	Command command = new Command(Command.Type.UNDO);
    	return command;
    }
}
