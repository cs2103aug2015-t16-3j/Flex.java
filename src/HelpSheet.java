import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelpSheet {
	Scene helpSheet;
	public Scene getHelpSheet(){
		return helpSheet;
	}
	public static void showHelpSheet(){
        Stage window = new Stage();
        window.setTitle("Help Sheet");
        window.setHeight(600);
        window.setWidth(950);
        
        //Function column for Deadline
        TableColumn<Method, Double> functionColumn = new TableColumn<>("Function");
        functionColumn.setMinWidth(400);
        functionColumn.setCellValueFactory(new PropertyValueFactory<>("function"));

        //howToUse column for Deadline
        TableColumn<Method, String> howToUseColumn = new TableColumn<>("How To Use");
        howToUseColumn.setMinWidth(550);
        howToUseColumn.setCellValueFactory(new PropertyValueFactory<>("howToUse"));

        //set up the table
        TableView<Method> table = new TableView<>();
        table.setItems(getMethod());
        table.getColumns().addAll(functionColumn, howToUseColumn);
        table.setMinHeight(500);
        GridPane.setConstraints(table, 0, 0);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.getChildren().addAll(table);
        Scene scene = new Scene(grid, 950, 600);

        window.setScene(scene);
        window.show();
	}
	public static ObservableList<Method> getMethod(){
		ObservableList<Method> methods = FXCollections.observableArrayList();
		//Deadline task
		methods.add(new Method("For deadlines",""));
		methods.add(new Method("Add a deadline task", "add <taskname>; by <end> on <date>"));
		methods.add(new Method("Add a done deadline task", "add <taskname>; by <end> on <date> [done]"));
		methods.add(new Method("Mark deadline task as done", "mark <date> <number> done"));
		methods.add(new Method("Mark deadline task as not done", "mark <date> <number> not done"));
		methods.add(new Method("Change the taskname of a deadline task", "change <date> <number> taskname to <newtaskname>"));
		methods.add(new Method("Change the date of a deadline task", "change <date> <number> date to <newdate>"));
		methods.add(new Method("Change the ending time of the deadline task", "change <date> <number> end by <new end>"));
		methods.add(new Method("Change the ending time and date of the deadline task", "change <date> <number> by <newend> on <newdate>"));
		
		//Event task
		methods.add(new Method("For events",""));
		methods.add(new Method("Add an event task", "add <taskname>; <start>-<end> on <date>; <priority>"));
		methods.add(new Method("Add a done event task", "add <taskname>; <start>-<end> on <date>; <priority> [done]"));
		methods.add(new Method("Mark an event task as done", "mark <date> <number> done"));
		methods.add(new Method("Mark an event task as not done", "mark <date> <number> not done"));
		methods.add(new Method("Change the taskname of an event task", "change <date> <number> taskname to <newtaskname>"));
		methods.add(new Method("Change the date of an event task", "change <date> <number> date to <newdate>"));
		methods.add(new Method("Change the priority of an event task", "change <date> <number> priority to <newpriority>"));
		methods.add(new Method("Change the start and end times of an event task", "change <date> <number> time to <newstart>-<newend>"));
		
		//Floating task
		methods.add(new Method("For floating tasks",""));
		methods.add(new Method("Add a floating task", "add <taskname>"));
		methods.add(new Method("Add a done floating task", "add <taskname> [done]"));
		methods.add(new Method("Mark a floating task as done", "mark floating <number> done"));
		methods.add(new Method("Mark a floating task as not done", "mark floating <number> not done"));
		methods.add(new Method("Change the taskname of a floating task", "change floating <number> taskname to <newtaskname>"));
		
		//Recurring task
		methods.add(new Method("For recurring tasks",""));
		methods.add(new Method("Add a recurring task", "add <taskname>; <start>-<end> every <day>"));
		methods.add(new Method("Change the taskname of a recurring task", "change rec <number> taskname to <newtaskname>"));
		methods.add(new Method("Change the day of a recurring task", "change rec <number> to every <newday>"));
		methods.add(new Method("Change the start and end times of a recurring task", "<start>-<end>: change rec <number> time to <newstart>-<newend>"));
		
		//Search functions
		methods.add(new Method("For function Search",""));
		methods.add(new Method("Search for a task using a matching string in the taskname", "search taskname <tasknamematchingstring>"));
		methods.add(new Method("Search for a task using an exact date", "search date <exactdate>"));
		methods.add(new Method("Search for a task using an exact day", "search day <exactday>"));
		methods.add(new Method("Search for a task using an exact start time", "search start <exactstartingtime>"));
		methods.add(new Method("Search for a task using an exact ending time", "search end <exactendingtime>"));
		methods.add(new Method("Search for a task using a matching string in the task" + "\n"  + "priority", "search priority <prioritymatchingstring>"));
		
		//Show functions
		methods.add(new Method("For function Show",""));
		methods.add(new Method("Show all tasks", "show all"));
		methods.add(new Method("Show all done tasks", "show done"));
		methods.add(new Method("Show all tasks which are not done", "show not done"));
		methods.add(new Method("Show all deadline tasks", "show deadline"));
		methods.add(new Method("Show all event tasks", "show event"));
		methods.add(new Method("Show all recurring tasks", "show recurring"));
		methods.add(new Method("Show all floating tasks", "show floating"));
		methods.add(new Method("Show all tasks by taskname", "show by taskname"));
		methods.add(new Method("Show all deadline and event tasks by date", "show by date"));
		methods.add(new Method("Show all recurring tasks by day", "show by day"));
		methods.add(new Method("Show all event tasks by start time", "show by start"));
		methods.add(new Method("Show all deadline and event tasks by end time", "show by end"));
		methods.add(new Method("Show all event tasks by priority", "show by priority"));
		methods.add(new Method("show the tasks for a given week (of seven days), given" + "\n" + "the starting(first) date of that week", "show week <exactstartingdate>"));
		
		return methods;
	}
}
