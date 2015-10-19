public class Command {
     public enum Type{
    	 ADD,DELETE,CHANGE,UNDO,DISPLAY,SEARCH,EXIT
     }
    
     private Type type;
     private String taskType;
     private String date;
     private String startTime;
     private String endTime;
     private String day;
     private String taskName;
     private String priority;
     private int number;
     
     public Command(Type type){
    	 this.type = type;
     }
     public Type getCommandType(){
    	 return type;
     }
     public String getTaskType(){
    	 return taskType;
     }
     public String getDate(){
    	 return date;
     }
     public String getstartTime(){
    	 return startTime;
     }
     public String getEndTime(){
    	 return endTime;
     }
     public String getDay(){
    	 return day;
     }
     public String getTaskName(){
    	 return taskName;
     }
     
     public String getPriority(){
    	 return priority;
     }
     
     public int getNumber(){
    	 return number;
     }
     
     public void setTaskType(String taskType){
    	 this.taskType = taskType;
     }
     public void setDate(String date){
    	 this.date = date;
     }
     public void setStartTime(String startTime){
    	 this.startTime = startTime;
     }
     public void setEndTime(String endTime){
    	 this.endTime = endTime;
     }
     public void setDay(String day){
    	 this.day = day;
     }
     
     public void setTaskName(String taskName){
    	 this.taskName = taskName;
     }
    
     public void setPriority(String priority){
    	 this.priority = priority;
     }
     public void setNumber(int number){
    	 this.number = number;
     }
     
}
