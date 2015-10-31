

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FlexWindow extends Application {
	private static TextField input;
	private static TextArea textArea;
	private static TextArea feedback;
	private static String command;
	private static Stage window;
	private static String filename;
	private static boolean haveFilename;
	private static Button help;
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:"
			+ "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\"
			+ "FlexTest" + "." + "txt";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		window.setTitle("Flex");

		input = new TextField();
		textArea = new TextArea(FILENAME_INPUT_MESSAGE);
		feedback = new TextArea();
		command = new String();
		haveFilename = false;
		help = new Button("Help");
		
		//Set GridPane
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(grid, 800, 600);
        
        //Set textArea
        textArea.setPrefColumnCount(40);
        textArea.setPrefRowCount(20);
        setWidthAndLength();
        setEditability();
        
        grid.add(help, 0, 0);
		grid.add(feedback, 0, 4);
        grid.add(input, 0, 5);
        grid.add(textArea, 0, 1);
		input.setPromptText("command");

		help.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				HelpSheet.showHelpSheet();
			}
			
		});
		
		//Action when enter is pressed
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode().equals(KeyCode.ENTER)){
					if(haveFilename){
						textArea.setText("");
						command = input.getText();
						filename = FindFilename.getFilename();
						Flex.processCommand(command, filename);
					}else{
						command = input.getText();
						textArea.setText("");
						haveFilename = FindFilename.find(command);
					}
					input.setText("");
				}
			}
			
		});
		
		//Request for closing
		window.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				event.consume();
				closeProgram();
			}
			
		});
        
		window.setScene(scene);
		window.show();
	}
	
	public void setEditability(){
		textArea.setEditable(false);
		feedback.setEditable(false);
	}
	
	public void setWidthAndLength(){
		textArea.setPrefHeight(600);
		input.setPrefWidth(800);
		feedback.setPrefHeight(20);
	}
	
	public static TextArea getFeedback(){
		return feedback;
	}
	
	public static TextArea getTextArea(){
		return textArea;
	}
	
	private static void closeProgram(){
		boolean result = ConfirmBox.display();
		if(result)
			window.close();
	}
}
