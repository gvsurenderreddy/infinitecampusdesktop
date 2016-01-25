/**
 * Created by jkoreth on 1/25/16.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GradesGUI extends Application {

    private TableView table = new TableView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Infinite Campus");
        stage.setWidth(700);
        stage.setHeight(433);
        table.setMinWidth(650);
        table.setMaxHeight(300);
        table.setEditable(false);
        final Label label = new Label("Grades - ");
        label.setFont(new Font("Arial", 20));

        TableColumn course = new TableColumn("Course");
        TableColumn assignment = new TableColumn("Assignment");
        TableColumn grade = new TableColumn("Grade");

        course.prefWidthProperty().bind(table.widthProperty().divide(2)); // w * 1/4
        assignment.prefWidthProperty().bind(table.widthProperty().divide(4)); // w * 2/4
        grade.prefWidthProperty().bind(table.widthProperty().divide(4)); // w * 1/4

        course.setResizable(false);
        assignment.setResizable(false);
        grade.setResizable(false);

        table.getColumns().addAll(course, assignment, grade);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }
}
