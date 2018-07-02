/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author USER
 */
public class LoginFXMLDocumentController implements Initializable {

    //why cant the fxml fields be static
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button button;
    @FXML
    private ImageView warningImage;
    @FXML
    private Label warningText;
    
    @FXML
    private void handleLoginAction(ActionEvent event) {
        
        warningImage.setVisible(false);
        warningText.setVisible(false);
        
        try {
            String uName = username.getText().trim();
            String pWord = password.getText().trim();
            
            if (uName == null || "".equals(uName) || pWord == null || "".equals(pWord)) {
                
                throw new Exception("Fields Cannot be empty");
                
            }
            
            Person person = new Teacher();
            /* Remember to change*/
            if (!person.login("2008162972", "password")) {
                throw new Exception("Incorrect Username and Password Combination");
            }
            
            switch (person.getAccessLevel()) {
                case STUDENT:
                    break;
                case TEACHER:
                    THomeFXMLDocumentController.setPerson(person);
                    ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("THomeFXMLDocument.fxml")));
                    break;
                case ADMIN:
                    
                    break;
                default:
                    break;
            }
            
        } catch (Exception ex) {
            
            warningText.setText(ex.getMessage());
            warningImage.setVisible(true);
            warningText.setVisible(true);
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
