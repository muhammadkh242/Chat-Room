/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;    

/**
 * FXML Controller class
 *
 * @author Muhammad
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button register;
    
    private Parent root;
    private Stage window;
    private Gateway gateway;
    private JSONObject obj;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //gateway = new Gateway();
        gateway = Gateway.getGatway();

        obj=new JSONObject();    

    }    

    @FXML
    private void handleRegister(ActionEvent event) {
        System.out.println("Register Clicked");
        obj.put("request", "register");
        obj.put("username", username.getText().trim());
        obj.put("password", password.getText().trim());
        String jString = obj.toJSONString();
        gateway.sendToServer(jString);
        username.clear();
        password.clear();
        goToScene("Login", register);
        
    }
    
    public void goToScene(String fxmlName, Button btn){
            try {
            root = FXMLLoader.load(getClass().getResource(fxmlName + ".fxml"));
            window = (Stage) btn.getScene().getWindow();
            window.setScene(new Scene(root,600,400));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
