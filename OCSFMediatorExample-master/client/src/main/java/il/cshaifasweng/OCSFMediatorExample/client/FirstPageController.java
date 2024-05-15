package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;


public class FirstPageController {

    @FXML
    private Button emergencyBtn;

    @FXML
    private ImageView image;

    @FXML
    private Button logInBtn;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Label textLable;

    @FXML
    void goToHomePage(ActionEvent event) throws IOException
    {
        String userName = getUserNameTextField();
        String password = getPasswordTextField();

        if (userName.matches("") || password.matches(""))
        {
            setTextLable("Please insert both userName and password!");
        }
        else
        {
            Message message = Message.newLogInMessage("go to home page", userName, password);
            SimpleClient.getClient().sendToServer(message);
        }
    }
    @FXML
    void onEmergency(ActionEvent event) throws IOException
    {
        String location = "demashq123";
        SimpleClient.getClient().sendToServer(Message.newEmergencyMessage("emergency case", location));
    }

    public String getPasswordTextField() {
        return passwordTextField.getText();
    }

    public String getUserNameTextField() {
        return userNameTextField.getText();
    }

    public void setTextLable(String textLable) {
        this.textLable.setText(textLable);
    }
}
