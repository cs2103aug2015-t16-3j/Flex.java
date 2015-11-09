//@@ author A0131830U
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
	      case COMMAND_SHOW: command = showCommand(arguments);
	                         break;
	      case COMMAND_MARK: command = markCommand(arguments);
	                         break;
	      case COMMAND_SEARCH: command = searchCommand(arguments);
	                         break;
	      case COMMAND_CLEAR: command = clearCommand();
	                         break;
	      case COMMAND_EXIT: command = exitCommand();
	                         break;
	      default: command = invalidCommand();
    	}
    	return command;
    	} 
    //split the input command into two parts
    private static ArrayList<String> splitString(String userInput){
    	String[] temp = userInput.trim().split(" ", 2);
    	return new ArrayList<String>(Arrays.asList(temp));
    }
    // return the command type
    private static String getCommandType(ArrayList<String> parameters){
    	return parameters.get(0);
    }
    //return the arguments
    private static String getArguments(ArrayList<String> parameters){
    	String nulltemp = "";
    	if(parameters.size()<2){
    		return nulltemp;
    	}
    	String temp = parameters.get(1);
    	return temp;
        }
    
    // add command type
    private static Command addCommand(String arguments) {
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
       	Command command = new Command(Command.Type.ADD);
    	if(arguments.contains("by")&&arguments.contains("on")){
    		command.setTaskType("Deadline");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String endTime = splitSec[1];
    		String date = splitSec[3];
    		if(arguments.contains("[done]")){
    			String statusD = "done";
    			command.setStatus(statusD);
    		}
    		command.setTaskName(taskName);
    		command.setEndTime(endTime);
    		command.setDate(date);
    		return command;
    	}
    	else if(arguments.contains("on")){
    		command.setTaskType("Event");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		if(splitFirst[2].contains("[done]")){
    			String[] splitFourth = splitFirst[2].trim().split(" ");
    			String priorityN = splitFourth[0];
    			String statusN = "done";
    			command.setPriority(priorityN);
    			command.setStatus(statusN);
    		}
    		else{
    		String priority = splitFirst[2];
    		command.setPriority(priority);
    		}
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String date = splitSec[2];
    		String[] splitThird = splitSec[0].trim().split("-");
    		String startTime = splitThird[0];
    		String endTime = splitThird[1];
    		
    		command.setTaskName(taskName);
    		command.setStartTime(startTime);
    		command.setEndTime(endTime);
    		command.setDate(date);
    		
    		return command;
    	}
    	else if(arguments.contains("every")){
    		command.setTaskType("Recurring");
    		String[] splitFirst = arguments.trim().split("; ");
    		String taskName = splitFirst[0];
    		String[] splitSec = splitFirst[1].trim().split(" ");
    		String day = splitSec[2];
    		String[] splitThird = splitSec[0].trim().split("-");
    		String startTime = splitThird[0];
    		String endTime = splitThird[1];
    		command.setTaskName(taskName);
    		command.setStartTime(startTime);
    		command.setEndTime(endTime);
    		command.setDay(day);
    		return command;
    	}
    	else{
    		command.setTaskType("Floating");
    		if(arguments.contains("[done]")){
    			String[] splitF = arguments.trim().split(" ");
    			String taskNameF = splitF[0];
    			String statusF = "done";
    			command.setTaskName(taskNameF);
    			command.setStatus(statusF);
    		}
    		else{
    		String taskName = arguments.trim();
    		command.setTaskName(taskName);
    		}
    		return command;
    	}
      
    }
    // delete command type
    private static Command deleteCommand(String arguments){
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
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
    // change command type
    private static Command changeCommand(String arguments) {
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
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
      		 if(splitFloFirst[2].contains("taskname to")){
      			 String[] splitFloSec = splitFloFirst[2].trim().split(" ");
      			 String newFloTaskName = splitFloSec[2].trim();
      			 command.setChangedTaskName(newFloTaskName);
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
       		if(splitFirst[2].contains("taskname")){
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
       		
       	}
    	return command;
     
 }
    //mark task as done or not done
    private static Command markCommand(String arguments){
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
    	Command command = new Command(Command.Type.MARK);
    	if(arguments.contains("not done")){
    		command.setStatus("not done");
    	}
    	else if(arguments.contains("done")){
    		command.setStatus("done");
    	}
    	return command;
    }
    // search command type
    private static Command searchCommand(String arguments){
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
    	
    	String[] splitFir = arguments.split(" ");
    	Command command = new Command(Command.Type.SEARCH);
    	command.setKeyWord(splitFir[1].trim());
    	if(arguments.contains("date")){
    		command.setSearchType("date");
        }
    	if(arguments.contains("taskname")){
    		command.setSearchType("taskname");
    	}
    	if(arguments.contains("day")){
    		command.setSearchType("day");
    	}
    	if(arguments.contains("start")){
    		command.setSearchType("startTime");
    	}
    	if(arguments.contains("end")){
    		command.setSearchType("endTime");
    	}
    	if(arguments.contains("priority")){
    		command.setSearchType("priority");
    	}
    	return command;
    }
   
    // show command type
    private static Command showCommand(String arguments){
    	if(arguments.trim().length()==0){
    		Command command = new Command(Command.Type.INVALID);
    		return command;
    	}
    	Command command = new Command(Command.Type.SHOW);
    	if(arguments.contains("by")){
    		String[] splitString = arguments.split(" ");
    		String splitKeyword = splitString[1].trim();
    		command.setShowKeyword(splitKeyword);
    	}
    	
    	else if(arguments.contains("week")){
    		String[] splitStringSec = arguments.split(" ");
    		String splitKeyWordWeek = splitStringSec[1].trim();
    		command.setShowKeyword(splitKeyWordWeek);
    	}
    	else if(!arguments.trim().equals("")){
    		String showKeyword = arguments.trim();
    		command.setShowKeyword(showKeyword);
    	}
    	return command;
    }
    
    // undo command type
    private static Command undoCommand(){
    	Command command = new Command(Command.Type.UNDO);
    	return command;
    }
    private static Command clearCommand(){
    	Command command = new Command(Command.Type.CLEAR);
    	return command;
    }
    // exit command type
    private static Command  exitCommand(){
    	Command command = new Command(Command.Type.EXIT);
    	return command;
    }
    
    //invalid command type
    private static Command invalidCommand(){
    	Command command = new Command(Command.Type.INVALID);
    	return command;
    }
}
