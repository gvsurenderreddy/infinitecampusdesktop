/**
 * Created by jkoreth on 1/25/16.
 */
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GradesGUI extends Application {

    private TableView<GradeTable> table = new TableView<GradeTable>();

    private ObservableList<GradeTable> data = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        Scene scene = new Scene(new Group());

        stage.setTitle("Infinite Campus");
        stage.setWidth(700);
        stage.setHeight(433);
        table.setMinWidth(650);
        table.setMaxHeight(300);
        table.setEditable(false);

        final Label label = new Label("Recent Assignments - ");
        label.setFont(new Font("Arial", 20));

        TableColumn time = new TableColumn("Time");
        TableColumn course = new TableColumn("Course");
        TableColumn assignment = new TableColumn("Assignment");
        TableColumn grade = new TableColumn("Grades");

        time.setSortable(false);
        course.setSortable(false);
        assignment.setSortable(false);
        grade.setSortable(false);

        time.setCellValueFactory(
                new PropertyValueFactory<GradeTable,String>("time")
        );
        course.setCellValueFactory(
                new PropertyValueFactory<GradeTable,String>("course")
        );
        assignment.setCellValueFactory(
                new PropertyValueFactory<GradeTable,String>("assignment")
        );
        grade.setCellValueFactory(
                new PropertyValueFactory<GradeTable,String>("grade")
        );

        
        data = Scraper.getGrades();

        table.setItems(data);
        table.getColumns().addAll(time, course, assignment, grade);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(label, table);
           
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

//        stage.getIcons().add(new Image("/home/jkoreth/School/AP_Computer_Science/Infinite Campus/Infinite Campus/src/campuslogo.png"));
        stage.setScene(scene);
        stage.show();


/*
  while(redo = true){
            ObservableList<GradeTable> scraper = Scraper.getGrades();
            if(scraper.equals(data)){
                // Do Nothing
            }
            else{
                data.removeAll(data);

                for (int i = 0; i < Scraper.getGrades().size(); i++) {
                    data.add(Scraper.getGrades().get(i));
                }
            }
        }*/
    }
}

