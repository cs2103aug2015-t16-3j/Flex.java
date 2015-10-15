public class LastAction {

	String[] lastActionStrings = new String[2];

	// lastActionStrings[0] will be "previousChangedTerm"
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

	public String getPreviousChangedTerm() {
		return this.lastActionStrings[0];
	}

	public String getPreviousAction() {
		return this.lastActionStrings[1];
	}

	public Task getPreviousTask() {
		return this.previousTask;
	}

	public void setPreviousChangedTerm(String tempChangedTerm) {
		this.lastActionStrings[0] = tempChangedTerm;
	}

	public void setPreviousAction(String tempAction) {
		this.lastActionStrings[1] = tempAction;
	}

	public void setPreviousTask(Task tempTask) {
		this.previousTask = tempTask;
	}

}
