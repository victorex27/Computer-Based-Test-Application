/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author USER
 */
public class LoginFXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            System.out.println("You clicked me!");
            Teacher teacher = new Teacher();
            teacher.setExam("Maths", "C:\\Users\\USER\\Desktop\\Peter Okonam\\CBT\\document\\Book2.csv");
            label.setText("Hello World!");
        } catch (Exception ex) {
            Logger.getLogger(LoginFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
