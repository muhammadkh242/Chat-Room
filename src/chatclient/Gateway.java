
package chatclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.simple.JSONObject;  
import org.json.simple.JSONValue; 

/**
 *
 * @author Muhammad
 */
public class Gateway {
    private PrintStream outToServer;
    private DataInputStream inputFromServer;
    private String response;
    private String messageToAll;
    static TextArea area;
    //singleton instance
    private static Gateway myGateway;
    //Constructor contain the connection to server
    public static synchronized Gateway getGatway(){
        if(myGateway == null){
            myGateway = new Gateway();
        }
        return myGateway;
    }
    private Gateway() {
        try {
            //create socket to connect to server
            Socket socket = new Socket("127.0.0.1", 6000);
            //create i/p o/p channels to communicate with server
            outToServer = new PrintStream(socket.getOutputStream());
            inputFromServer = new DataInputStream(socket.getInputStream());
            //Thread to listen server reply
            Thread th  = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            response = inputFromServer.readLine();
                            //System.out.println(response);
                            switch(response){
                                case"Login Successfully":
                                    System.out.println("Login Successfully_____");
                                    Platform.runLater(() -> {                                
                                        try {
                                            Parent root = FXMLLoader.load(ChatClient.class.getResource("Chat.fxml"));
                                            ChatClient.getMainStage().setScene(new Scene(root, 600, 400));
                                            ChatClient.getMainStage().show();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    break;

                                case"Login Failed":
                                    Platform.runLater(() -> { 
                                        try {
                                            Parent root = FXMLLoader.load(ChatClient.class.getResource("Login.fxml"));
                                            ChatClient.getMainStage().setScene(new Scene(root, 600, 400));
                                            ChatClient.getMainStage().show();
                                            System.out.println("Login Failed________");
                                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                                            al.setContentText("Wrong Username or Password Please try to login again");
                                            al.show();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });

                                    break;
                                default:
                                    System.out.println("message to all");
                                    //System.out.println(response);
                                    Object obj=JSONValue.parse(response);  
                                    JSONObject jsonObject = (JSONObject) obj; 
                                    String username = (String) jsonObject.get("username");
                                    String msg = (String) jsonObject.get("msg");
                                    messageToAll = username + " --> " + msg;

                                    area.appendText(messageToAll + "\n");


                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            th.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //send some msg to test connection
    public synchronized void sendToServer(String msg){
        System.out.println("in toServer");
        outToServer.println(msg);
        outToServer.flush();
    }
    
    
    public synchronized PrintStream getPrintStream(){
        return outToServer;
    }
    
//    public static void main(String[] args) {
//        new Gateway();
//                
//    }
    
}
