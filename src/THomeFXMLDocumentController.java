

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Frame.Person;
import Frame.Teacher;
import Frame.Course;
import Frame.Student;
import Frame.StudentQuestionFormatFXMLController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class THomeFXMLDocumentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label fullName;
    @FXML
    private Pane rightPane;
    @FXML
    private VBox vBox;
    @FXML
    private Pane dragPane;

    private static Person person;
    private static Student student;
    private static Teacher teacher;
    private static Course currentCourse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // initialize fullName label

            fullName.setText(person.getFullName());

            /**
             * Clean this up*
             */
            if (person instanceof Teacher) {

                teacher = ((Teacher) person);

                showTeacherView(teacher);

            }
            if (person instanceof Student) {

                student = ((Student) person);

                student.setDepartment();
                student.retrieveCourses();

                showStudentView(student);

            }

        } 
        catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This displays the view only for students * *
     */
    private void showStudentView(Student student) throws ClassNotFoundException, Exception {

        if (student.getCourses().size() < 1) {
            throw new Exception("No Registered Course for student ");
        }

        student.getCourses().forEach(a -> {

            Hyperlink link = new Hyperlink();

            link.setText(a.getCourseCode());
            link.setOnAction(e -> {

                try {

                    AnchorPane anchor;

                    setCurrentCourse(a);
                    dragPane.setVisible(false);
                    vBox.setVisible(false);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Frame/StudentQuestionFormatFXML.fxml"));
                    try {
                        anchor = (AnchorPane) loader.load();
                        StudentQuestionFormatFXMLController controller = loader.getController();
                        //controller.setQuestions(student.getId(), student.getAllQuestions(a.getCourseCode()));
                        
                        controller.play(student, a.getCourseCode());

                        //rightPane.getChildren().clear();
                        rightPane.getChildren().add(anchor);

                    } catch (IOException ex) {
                        Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            vBox.getChildren().add(link);

        });

    }

    /**
     * This displays the view only for teachers * *
     */
    private void showTeacherView(Teacher teacher) throws ClassNotFoundException, Exception {

        teacher.getCourses().forEach(a -> {

            Hyperlink link = new Hyperlink();

            link.setText(a.getCourseCode());
            link.setOnAction(e -> {

                setCurrentCourse(a);
                dragPane.setVisible(true);
                vBox.setVisible(false);
            });

            vBox.getChildren().add(link);

        });

        //rightPane.getChildren().clear();
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("Frame/QuestionFXML.fxml"));
        //AnchorPane anchor = (AnchorPane)loader.load();
        //QuestionFXMLController controller = loader.getController();
        //controller.setTextFields("kjdfsjkjd", "fdf", "ff", "ddd", "dffd", "fddd");
        //controller.setQuestion("Hello");
        //controller.setOptionD("garri");
        //rightPane.getChildren().add(anchor);
    }

    public static void setPerson(Person _person) {

        person = _person;
    }

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
    }

    @FXML
    public void onCustomDragEvent(DragEvent event) {

        Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, "File Upload Starting....", "");

        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file : db.getFiles()) {
                filePath = file.getAbsolutePath();
                //System.out.println(filePath);

                try {

                    teacher.setExam(currentCourse.getCourseCode(), filePath);
                } catch (Exception ex) {
                    Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        event.setDropCompleted(success);
        event.consume();

    }

    @FXML
    public void onCustomDragOver(DragEvent event) {

        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }

    }

}
