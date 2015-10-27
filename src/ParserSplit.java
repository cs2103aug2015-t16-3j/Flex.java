import java.util.*;
public class ParserSplit {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_CHANGE = "change";
    private static final String COMMAND_UNDO = "undo";
    //private static final String COMMAND_SHOW = "show";
    //private static final String COMMAND_DISPLAY = "display";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_EXIT = "exit";
    
    
    
    
    public static Command parse(String userInput){
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
	      //case COMMAND_SHOW: command = showCommand(arguments);
	                         //break;
	      //case COMMAND_DISPLAY: command = displayCommand(arguments);
	                         //break;
	      case COMMAND_SEARCH: command = searchCommand(arguments);
	                         break;
	      case COMMAND_EXIT: command = exitCommand();
	                         break;
	      default: command = invalidCommand();
    	}
    	return command;
    	} 
    private static ArrayList<String> splitString(String userInput){
    	String[] temp = userInput.trim().split(" ", 2);
    	return new ArrayList<String>(Arrays.asList(temp));
    }
    private static String getCommandType(ArrayList<String> parameters){
    	return parameters.get(0);
    }
    private static String getArguments(ArrayList<String> parameters){
    	String temp = parameters.get(1);
    	return temp;
        }
    
    // add
    private static Command addCommand(String arguments){
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
    		command.setTaskType("Floating");
    		String taskName = arguments.trim();
    		command.setTaskName(taskName);
    		return command;
    	}
    }
    // delete
    private static Command deleteCommand(String arguments){
    	Command command = new Command(Command.Type.DELETE);
    	if(arguments.contains("floating")){
    		command.setTaskType("Floating");
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
    // change
    private static Command changeCommand(String arguments) {
    	Command command = new Command(Command.Type.CHANGE);
    	if(arguments.contains("rec")){
    		command.setTaskType("Recurring");
    		String[] splitRecFirst = arguments.trim().split(" ", 3);
    		int number = Integer.valueOf(splitRecFirst[1]);
    		command.setNumber(number);
    		if(splitRecFirst[2].contains("taskname")){
    			String[] splitRecSec = splitRecFirst[2].trim().split(" ");
    			String newRecTaskName = splitRecSec[2].trim();
    			command.setChangedTaskName(newRecTaskName);
    		}
    		else if(splitRecFirst[2].contains("every")){
    			String[] splitRecThird = splitRecFirst[2].trim().split(" ");
    			String newDay = splitRecThird[2].trim();
    			command.setChangedDay(newDay);
    		}
    		else if(splitRecFirst[2].contains("time")){
    			String[] splitRecFourth = splitRecFirst[2].trim().split(" ");
    			String startEndTime = splitRecFourth[2].trim();
    			String[] splitRecFifth = startEndTime.split("-");
    			String newStart = splitRecFifth[0].trim();
    			String newEnd = splitRecFifth[1].trim();
    			command.setChangedStartTime(newStart);
    			command.setChangedEndTime(newEnd);
    		}
        }
    	else if(arguments.contains("floating")){
      		 command.setTaskType("floating");
      		 String[] splitFloFirst = arguments.trim().split(" ",3);
      		 int FloNumber = Integer.valueOf(splitFloFirst[1]);
      		 command.setNumber(FloNumber);
      		 if(splitFloFirst[2].contains("to")){
      			 String[] splitFloSec = splitFloFirst[2].trim().split(" ");
      			 String newFloTaskName = splitFloSec[1].trim();
      			 command.setChangedTaskName(newFloTaskName);
      		 }
      		 else if(splitFloFirst[2].trim().equals("done")){
      			 command.setStatus("done");
      		 }
      		 else if(splitFloFirst[2].trim().equals("not done")){
      			 command.setStatus("not done");
      		 }
      	}
    	else if(arguments.contains("by")){
       		command.setTaskType("Deadline");
       		String[] splitDeadFirst = arguments.trim().split(" ", 3);
       		String deadDate = splitDeadFirst[0].trim();
       		int deadNumber = Integer.valueOf(splitDeadFirst[1]);
       		command.setDate(deadDate);
       		command.setNumber(deadNumber);
       		if(splitDeadFirst[2].contains("end by")){
       			String[] splitDeadSec = splitDeadFirst[2].trim().split(" ");
       			String deadEnd = splitDeadSec[2].trim();
       			command.setChangedEndTime(deadEnd);
       		}
       		else if(splitDeadFirst[2].contains("by") && splitDeadFirst[2].contains("on")){
       			String[] splitDeadThird = splitDeadFirst[2].trim().split(" ");
       			String newdeadEnd = splitDeadThird[1].trim();
       			String newdeadDate = splitDeadThird[3].trim();
       			command.setChangedEndTime(newdeadEnd);
       			command.setChangedDate(newdeadDate);
       		}
       	}
    	else{
       		command.setTaskType("Event");
       		String[] splitFirst = arguments.trim().split(" ", 3);
       		String date = splitFirst[0];
       		int number = Integer.valueOf(splitFirst[1]);
       		command.setDate(date);
       		command.setNumber(number);
       		if(splitFirst[2].contains("taskName")){
       			String[] splitSec = splitFirst[2].trim().split(" ");
       			String newTaskName = splitSec[2].trim();
       			command.setChangedTaskName(newTaskName);
       		}
       		else if(splitFirst[2].contains("date")){
       			String[] splitThird = splitFirst[2].trim().split(" ");
       			String newDate = splitThird[2].trim();
       			command.setChangedDate(newDate);
       		}
       		else if(splitFirst[2].contains("priority")){
       			String[] splitFourth = splitFirst[2].trim().split(" ");
       			String newPriority = splitFourth[2].trim();
       			command.setChangedPriority(newPriority);
       		}
       		else if(splitFirst[2].contains("time")){
       			String[] splitFifth = splitFirst[2].trim().split(" ");
       			String newTime = splitFifth[2];
       			String[] newTimeSplit = newTime.trim().split("-");
       			String newStartTime = newTimeSplit[0];
       			String newEndTime = newTimeSplit[1];
       			command.setChangedStartTime(newStartTime);
       			command.setChangedEndTime(newEndTime);
       		}
       		else if(splitFirst[2].trim().equals("not done")){
       			command.setStatus("not done");
       		}
       		else if(splitFirst[2].trim().equals("done")){
       			command.setStatus("done");
       		}
       	}
    	return command;
     
 }

    // search
    private static Command searchCommand(String arguments){
    	Command command = new Command(Command.Type.SEARCH);
    	String keyWord = arguments.trim();
    	command.setKeyWord(keyWord);
    	return command;
    }
    
    // undo
    private static Command undoCommand(){
    	Command command = new Command(Command.Type.UNDO);
    	return command;
    }
    
    // exit 
    private static Command  exitCommand(){
    	Command command = new Command(Command.Type.EXIT);
    	return command;
    }
    
    //invalid
    private static Command invalidCommand(){
    	Command command = new Command(Command.Type.INVALID);
    	return command;
    }
}
