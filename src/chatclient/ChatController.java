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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author Muhammad
 */
public class ChatController implements Initializable {

    @FXML
    private TextArea Area;
    @FXML
    private TextArea message;
    @FXML
    private Button send;
    @FXML
    private Button back;
    private Gateway gateway;    
    private Parent root;
    private Stage window;
    private JSONObject obj;
    static String username;
    static String msgToAll;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Area.setFocusTraversable(false);
        send.setFocusTraversable(false);
        //gateway = new Gateway();
        gateway = Gateway.getGatway();

        Gateway.area = Area;
        obj=new JSONObject();    

    }    

    @FXML
    private void handleSend(ActionEvent event) {
        System.out.println("Send");
        String msg = message.getText();
        obj.put("request", "message");
        obj.put("username", username);
        obj.put("msg", msg);
        String jString = obj.toJSONString();
        System.out.println(jString + "in send handling");
        gateway.sendToServer(jString);
        //gateway.getPrintStream().println(jString);
        message.clear();

    }

    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("Back");

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                    System.out.println("Back to login");
                    Platform.runLater(() -> {goToScene("Login", back);
                    });
            }
        });
        th.start();

        
    }
    public void setArea(String msg){
        Area.setText(msg);
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
    
}
