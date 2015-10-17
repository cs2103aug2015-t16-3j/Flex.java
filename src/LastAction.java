public class LastAction {

	
	
	private String[] lastActionStrings = new String[3];
	
	// lastActionStrings[0] will be "previousChangedTerm"
	// this will not be null if there is at least one changed term
	// previousChangedTerm refers to the original term before the change

	// lastActionStrings[1] will be "previousAction"
	// the last add, delete or change action

	// lastActionStrings[2] will be the previous type of task handled 
	// meaning
	// "floating", "deadline", "event" or "recurring"
	
	// "previousTask" refers to
	// 1. A task which has been added,
	// 2. A task which has been deleted
	// OR
	// 3. A task which has been changed (the state of the task AFTER it has been
	// changed)
	private Task previousTask = new Task();
	
	// taskIndex will store the index, which is 0 or a positive integer (starts from 0)
	// in "ArrayList<String> allTasksList"
	private int taskIndex = 0;
	
		
	public String getPreviousChangedTerm() {
		return this.lastActionStrings[0];
	}

	public String getPreviousAction() {
		return this.lastActionStrings[1];
	}

	public String getTaskType(){
		return this.lastActionStrings[2];
	}	
		
	public Task getPreviousTask() {
		return this.previousTask;
	}
	
	public int getTaskIndex(){
		return this.taskIndex;
	}

	public void setPreviousChangedTerm(String tempChangedTerm) {
		this.lastActionStrings[0] = tempChangedTerm;
	}

	public void setPreviousAction(String tempAction) {
		this.lastActionStrings[1] = tempAction;
	}
	
	public void setTaskType(String newTaskType){
		this.lastActionStrings[2] = newTaskType;
	}

	public void setPreviousTask(Task tempTask) {
		this.previousTask = tempTask;
	}

	
	public void setTaskIndex(int newNumber){
		this.taskIndex = newNumber;
	}
	
	
}
