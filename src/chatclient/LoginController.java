/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button signup;
    private Parent root;
    private Stage window;
    private Gateway gateway;
    private JSONObject obj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //gateway = new Gateway();
        gateway = Gateway.getGatway();
        obj=new JSONObject();    

    }    

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Login Clicked");
        obj.put("request", "login");
        obj.put("username", username.getText().trim());
        obj.put("password", password.getText().trim());
        String jString = obj.toJSONString();
        gateway.sendToServer(jString);
        ChatController.username = username.getText().trim();
        username.clear();
        password.clear();
        
        
    }

    @FXML
    private void handleSignup(ActionEvent event) {
        try {
            System.out.println("signup Clicked");
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            window = (Stage) signup.getScene().getWindow();
            window.setScene(new Scene(root,600,400));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    
    public void goToScene(String fxmlName, Button btn){
        System.out.println("Entered goto scene");

        try {
        root = FXMLLoader.load(getClass().getResource(fxmlName + ".fxml"));
        window = (Stage) btn.getScene().getWindow();
        window.setScene(new Scene(root,600,400));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void goToChat(){
        System.out.println("Entered goto chat");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    goToScene("Chat", login);
                });
            }
        });
        th.start();
    }
}
