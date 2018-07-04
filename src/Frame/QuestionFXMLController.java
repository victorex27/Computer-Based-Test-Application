/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class QuestionFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Pane paneQuestion;
    @FXML
    TextArea questionArea;
    @FXML
    Pane paneA;
    @FXML
    TextField questionATextField;
    @FXML
    Pane paneB;
    @FXML
    TextField questionBTextField;
    @FXML
    Pane paneC;
    @FXML
    TextField questionCTextField;
    @FXML
    Pane paneD;
    @FXML
    TextField questionDTextField;
    @FXML
    Pane paneE;
    @FXML
    TextField questionETextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setQuestion(String question) {

        questionArea.setText(question);
    }

    public void setOptionA(String optionA) {

        questionATextField.setText(optionA);
    }

    public void setOptionB(String optionB) {
        questionBTextField.setText(optionB);

    }

    public void setOptionC(String optionC) {

        questionCTextField.setText(optionC);
    }

    public void setOptionD(String optionD) {
        questionDTextField.setText(optionD);
    }

    public void setOptionE(String optionE) {
        questionETextField.setText(optionE);
    }

    public String getQuestion() {
        return questionArea.getText();
    }

    public String getOptionA() {
        return questionATextField.getText();
    }

    public String getOptionB() {
        return questionBTextField.getText();
    }

    public String getOptionC() {
        return questionCTextField.getText();
    }

    public String getOptionD() {
        return questionDTextField.getText();
    }

    public String getOptionE() {
        return questionETextField.getText();
    }

}
