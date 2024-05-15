package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static Stage primaryStage;  // static reference to  primary Stage
    private SimpleClient client;

    @Override
    public void start(Stage stage) throws IOException
    {
        primaryStage = stage;  // Initialize  primary stage reference
    	EventBus.getDefault().register(this);
    	client = SimpleClient.getClient();
    	client.openConnection();

        scene = new Scene(loadFXML("firstPage"), 860, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    static void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getPrimaryStage()
    {
        return primaryStage;  // Provide a public accessor for the primary Stage
    }

    @Override
	public void stop() throws Exception
    {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
		super.stop();
	}


    @Subscribe
    public void onWarningEvent(WarningEvent event)
    {
        if (event.getWarning().getMessage().startsWith("Emergency case received"))
        {
                        Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("%s\nTime: %s\n",
                                "Your emergency report received!",
                                event.getWarning().getTime().toString()));
                alert.setHeaderText("Message");
                alert.show();
            });
        }
        else
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format("%s\nTime: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });
    }

}