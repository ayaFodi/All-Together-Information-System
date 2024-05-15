package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {
	private static SimpleClient client = null;
	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) throws Exception , ClassNotFoundException
	{

		if (msg.getClass().equals(Warning.class))
		{
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else
		{
			Message message = (Message) msg;
			if(message.getMessage().startsWith("Emergency case received"))
			{
				EventBus.getDefault().post(new WarningEvent(new Warning("Emergency case received!")));
			}
			else if (message.getMessage().startsWith("Authentication successful"))
			{
				// This must be run on the JavaFX Application Thread
				Platform.runLater(() -> {
					try {
						// Load the homePage.fxml file
						FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
						Parent root = loader.load();

						// Optionally, get the controller and perform any initialization
						HomePageController controller = loader.getController();
						controller.initializePage(message.getClientId(), message.getIsManager(), message.getUserDTO(), message.getUserName());

						// Show the new scene
						Scene scene = new Scene(root, 1050, 700);
						Stage stage = App.getPrimaryStage();  // Assume a method that retrieves the main stage

						App.setRoot("homePage");
						stage.setScene(scene);
						stage.show();


					} catch (IOException e) {
						e.printStackTrace();
						// Handle any IO exceptions (e.g., file not found)
					}
				});
			}
			else if (message.getMessage().startsWith("community listPage"))
			{
				EventBus.getDefault().post(MessageEvent.newUserListMessage("community listPage", message.getUserDTOList()));
			}
			else if (message.getMessage().startsWith("emergency list"))
			{
				EventBus.getDefault().post(MessageEvent.newEmergencyListMessage(message.getMessage(), message.getEmergencyDisplayList()));
			}
			else if (message.getMessage().startsWith("personal information"))
			{
				EventBus.getDefault().post(MessageEvent.newUserListMessage("personal information", message.getUserDTOList()));
			}
		}
	}
	public static SimpleClient getClient()
	{
		if (client == null)
		{
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
