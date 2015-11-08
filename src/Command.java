
public class Command {
     public enum Type{
    	 ADD,DELETE,CHANGE,UNDO,SHOW,MARK,SEARCH,CLEAR,EXIT,INVALID
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
     private String keyWord;
     private String status;
     private String changedTaskName;
     private String changedDate;
     private String changedStartTime;
     private String changedEndTime;
     private String changedDay;
     private String changedPriority;
     private String showKeyword;
     private String searchType;
     
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
     public String getKeyWord(){
    	 return keyWord;
     }
     public String getStatus(){
    	 return status;
     }
     public String getChangedTaskName(){
    	 return changedTaskName;
     }
     public String getChangedDate(){
    	 return changedDate;
     }
     public String getChangedStartTime(){
    	 return changedStartTime;
     }
     public String getChangedEndTime(){
    	 return changedEndTime;
     }
     public String getChangedDay(){
    	 return changedDay;
     }
     public String getChangedPriority(){
    	 return changedPriority;
     }
     public String getShowKeyword(){
    	 return showKeyword;
     }
     public String getSearchType(){
    	 return searchType;
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
     public void setKeyWord(String keyWord){
    	 this.keyWord = keyWord;
     }
     public void setStatus(String status){
    	 this.status = status;
     }
     public void setChangedTaskName(String changedTaskName){
    	 this.changedTaskName = changedTaskName;
     }
     public void setChangedDate(String changedDate){
    	 this.changedDate = changedDate;
     }
     public void setChangedStartTime(String changedStartTime){
    	 this.changedStartTime = changedStartTime;
     }
     public void setChangedEndTime(String changedEndTime){
    	 this.changedEndTime = changedEndTime;
     }
     public void setChangedDay(String changedDay){
    	 this.changedDay = changedDay;
     }
     public void setChangedPriority(String changedPriority){
    	 this.changedPriority = changedPriority;
     }
     public void setShowKeyword(String showKeyword){
    	 this.showKeyword = showKeyword;
     }
     public void setSearchType(String searchType){
    	 this.searchType = searchType;
     }
}