package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.EmergencyDisplay;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.UserDTO;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class HomePageController {
    @FXML
    private Label timeLabel;
    @FXML
    private Button LogOutBtn;
    @FXML
    private Label WelcomeUserLabel;
    @FXML
    private Button displayBtn;
    @FXML
    private HBox emerTableHB;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private ComboBox<Integer> fromHourComBox;
    @FXML
    private ComboBox<Integer> toHourComBox;
    @FXML
    private ComboBox<String> communityComBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Button toCommunityListBtn;
    @FXML
    private Button toEmergencyListBtn;
    @FXML
    private GridPane grindPane;
    @FXML
    private GridPane personalInfoGrindPane;

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label IDnumberLabel;
    @FXML private Label userNameLabel;
    @FXML private Label communityLabel;
    @FXML private Label locationLabel;
    @FXML private Label yourManagerLabel;

    @FXML
    private Button toPersdonalInformationBtn;

    @FXML
    private TableView<EmergencyDisplay> emergencysTable;

    @FXML private TableColumn<EmergencyDisplay, Integer> emerIdECol;
    @FXML private TableColumn<EmergencyDisplay, LocalTime> TimeECol;
    @FXML private TableColumn<EmergencyDisplay, LocalDate> dateECol;
    @FXML private TableColumn<EmergencyDisplay, Integer> userIdECol;
    @FXML private TableColumn<EmergencyDisplay, String> locationECol;
    @FXML private TableColumn<EmergencyDisplay, String> communityECol;
    @FXML private TableColumn<EmergencyDisplay, String> firstNameECol;
    @FXML private TableColumn<EmergencyDisplay, String> lastNameECol;
    @FXML private TableColumn<EmergencyDisplay, String> typeECol;

    @FXML
    private TableView<UserDTO> usersTable;

    @FXML private TableColumn<UserDTO, Integer> userIdCol;
    @FXML private TableColumn<UserDTO, String> firstNameCol;
    @FXML private TableColumn<UserDTO, String> lastNameCol;
    @FXML private TableColumn<UserDTO, String> communityCol;
    @FXML private TableColumn<UserDTO, String> locationCol;

    private List<UserDTO> users = new ArrayList<>();
    private List<EmergencyDisplay> emergencys = new ArrayList<>();
    private Boolean isManager;

    private int clientId;

    private UserDTO userDTO;

    @FXML
    void initialize()
    {
        EventBus.getDefault().register(this);
        userDTO = new UserDTO();

    }
    public void initializePage(int clientId ,boolean isManager,UserDTO userDTO,String userName)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Create a timeline for a clock that updates every second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(dtf)); // Update the label every second
        }));

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();


        this.userDTO=userDTO;
        this.clientId=clientId;
        this.isManager=isManager;

        userNameLabel.setText(userName);

        WelcomeUserLabel.setText("Welcome " + userDTO.getFirstName() );
        initializeWithManager();
    }
    private void initializeWithManager()
    {
        // Additional initialization based on isManager
        if (isManager == true) {
            toCommunityListBtn.setVisible(true);
            toEmergencyListBtn.setVisible(true);
            communityComBox.getItems().add("my community");
            communityComBox.getItems().add("all communities");

            // Populate hours as integers from 0 to 23
            for (int i = 0; i < 24; i++) {
                fromHourComBox.getItems().add(i);
                toHourComBox.getItems().add(i);
            }
        }
    }
    public void resetController()
    {
        // Reset all relevant fields
        this.userDTO = null;
        this.clientId = 0;
        this.isManager = false;
    }
    private void unvisibleAll()
    {
        emerTableHB.setManaged(false);
        emerTableHB.setVisible(false);

        usersTable.setManaged(false);
        usersTable.setVisible(false);

        grindPane.setManaged(false);
        grindPane.setVisible(false);

        personalInfoGrindPane.setManaged(false);
        personalInfoGrindPane.setVisible(false);

        emerTableHB.setManaged(false);
        emerTableHB.setVisible(false);
    }
    @FXML
    void displayEmergenceList(ActionEvent event) throws Exception
    {
        unvisibleAll();

        grindPane.setManaged(true);
        grindPane.setVisible(true);

        emerTableHB.setManaged(true);
        emerTableHB.setVisible(true);

        LocalDate fromDate;
        LocalTime fromTime;

        LocalDate toDate;
        LocalTime toTime;

       fromDate=fromDatePicker.getValue();
       toDate=toDatePicker.getValue();


        fromTime=LocalTime.of(fromHourComBox.getValue(),00);
        toTime=LocalTime.of(toHourComBox.getValue(),59);

        LocalDateTime fromDateTime = fromDate.atTime(fromTime);
        LocalDateTime toDateTime = toDate.atTime(toTime);
        if (fromDateTime.isAfter(toDateTime))
        {
             EventBus.getDefault().post(new WarningEvent(new Warning("Warning: illegal date time ")));
        }
      else
      {
          String m;
          if (communityComBox.getValue()=="all communities") {
              m = "show emergency list";
              setTitleLabel("Emergency list of all communitys:");
          }
          else {
              m = "show emergency list of my community";
              setTitleLabel("Emergency list of your community:");
          }
        Message message = Message.newEmergencyListToServerMessage(m,fromDateTime,toDateTime,getClientId());
        SimpleClient.getClient().sendToServer(message);
       }
    }


    @FXML
    void showEmergencyList(ActionEvent event) throws IOException
    {
        unvisibleAll();

        grindPane.setManaged(true);
        grindPane.setVisible(true);

        initializeEmergencyTable();

        communityComBox.getSelectionModel().select("my community");
        // Optionally set a default value
        fromHourComBox.getSelectionModel().select(Integer.valueOf(0)); // Selects 0 as the default hour
        // Optionally set a default value
        toHourComBox.getSelectionModel().select(Integer.valueOf(23)); // Selects 0 as the default hour
        fromDatePicker.setValue(LocalDate.of(2000,1,1));
        toDatePicker.setValue(LocalDate.now());
        setTitleLabel("Choose a time domain and community");
    }

    @FXML
    void showPersonalInformation(ActionEvent event) throws Exception
    {
        unvisibleAll();

        personalInfoGrindPane.setManaged(true);
        personalInfoGrindPane.setVisible(true);

        titleLabel.setText("Your personal information:");
        firstNameLabel.setText(userDTO.getFirstName());
        lastNameLabel.setText(userDTO.getLastName());
        IDnumberLabel.setText( userDTO.getIdNumtoString());
        communityLabel.setText(userDTO.getCommunity());
        locationLabel.setText(userDTO.getLocation());
        yourManagerLabel.setText(userDTO.getManagerName());
    }

    @FXML
    void onLogOut(ActionEvent event) {
        resetController();
        // This must be run on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                // Load the firstPage.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
                Parent root = loader.load();

                // Show the new scene
                Scene scene = new Scene(root, 860, 600);
                Stage stage = App.getPrimaryStage();  // Assume a method that retrieves the main stage

                App.setRoot("firstPage");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any IO exceptions (e.g., file not found)
            }
        });

    }

    @FXML
    void showCommunityList(ActionEvent  event) throws IOException
    {
        unvisibleAll();

        usersTable.setManaged(true);
        usersTable.setVisible(true);
        setTitleLabel("Your community list:");

        initializeCommunityListTable();

        Message message =  Message.newCommunityListMessage("show community list",getClientId());
        SimpleClient.getClient().sendToServer(message);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) throws Exception
    {
        if (event.getMessage().startsWith("community listPage"))
        {
            users = event.getCommunityMemList();
            ObservableList<UserDTO> observableUsers = FXCollections.observableArrayList(users);
           usersTable.setItems(observableUsers);
        }
        else if (event.getMessage().startsWith("emergency list"))
        {
            emergencys = event.getEmergencyDisplayList();
            ObservableList<EmergencyDisplay> observableEmergencys = FXCollections.observableArrayList(emergencys);
            emergencysTable.setItems(observableEmergencys);
        }

    }

private void initializeCommunityListTable()
{
    userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    communityCol.setCellValueFactory(new PropertyValueFactory<>("community"));
    locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
}
    private void  initializeEmergencyTable()
    {
        emerIdECol.setCellValueFactory(new PropertyValueFactory<>("emergencyId"));
        TimeECol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getEmergencyDateTime().toLocalTime()));
        dateECol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getEmergencyDateTime().toLocalDate()));
        userIdECol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        locationECol.setCellValueFactory(new PropertyValueFactory<>("location"));
        communityECol.setCellValueFactory(new PropertyValueFactory<>("community"));
        firstNameECol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameECol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        typeECol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }


    public void setTitleLabel(String titleLabel)
    {
        this.titleLabel.setText(titleLabel);
    }
    public void setFirstNameLabel(String firstNameLabel)
{
    this.firstNameLabel.setText(firstNameLabel);
}
    public int getClientId()
    {
        return clientId;
    }
    public Boolean getIsManager()
    {
        return isManager;
    }

}
