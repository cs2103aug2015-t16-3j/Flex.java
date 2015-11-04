import java.util.ArrayList;

public class LastAction {

	private String[] lastActionStrings = new String[3];

	// lastActionStrings[0] will be "previousChangedScheduleString"
	// this will not be null if there is at least one changed term
	// previousChangedTerm refers to the original term before the change

	// lastActionStrings[1] will be "previousAction"
	// the last add, delete or change action

	// "previousTask" refers to
	// 1. A task which has been added,
	// 2. A task which has been deleted
	// OR
	// 3. A task which has been changed (the state of the task AFTER it has been
	// changed)
	private Task previousTask = new Task();

	private ArrayList<Task> clearTaskList = new ArrayList<Task>();

	public ArrayList<Task> getClearTaskList() {
		return this.clearTaskList;
	}

	public void setClearTaskList(ArrayList<Task> tempList) {

		this.clearTaskList.clear();

		for (int i = 0; i < tempList.size(); i++) {
			this.clearTaskList.add(tempList.get(i));
		}

	}

	public String getPreviousChangedScheduleString() {
		return this.lastActionStrings[0];
	}

	public String getPreviousAction() {
		return this.lastActionStrings[1];
	}

	public String getTaskType() {
		return this.lastActionStrings[2];
	}

	public Task getPreviousTask() {
		return this.previousTask;
	}

	public void setPreviousChangedScheduleString(String tempPreviousChangedScheduleString) {
		this.lastActionStrings[0] = tempPreviousChangedScheduleString;
	}

	public void setPreviousAction(String tempAction) {
		this.lastActionStrings[1] = tempAction;
	}

	public void setPreviousTask(Task tempTask) {
		this.previousTask = tempTask;
	}

}
