import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    public static boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Exit");
        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Exit Flex?");

        Button yesButton = new Button();
        yesButton.setText("Yes");
        Button noButton = new Button();
        noButton.setText("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout1 = new VBox(10);
        HBox layout2 = new HBox(2);
        layout2.getChildren().addAll(yesButton, noButton);
        layout2.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label, layout2);
        layout1.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout1);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
