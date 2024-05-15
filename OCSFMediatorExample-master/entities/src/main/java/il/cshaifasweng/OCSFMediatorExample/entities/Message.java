package il.cshaifasweng.OCSFMediatorExample.entities;
import org.w3c.dom.Node;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Message implements Serializable {

    private int clientId;
    private String message;
    private String location;
    private List<UserDTO> userDTOList=new ArrayList<>();
    private UserDTO userDTO;
    private String userName;
    private String password;
    private Boolean isManager;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private List<EmergencyDisplay> emergencyDisplayList=new ArrayList<>();


    private Message(String message)
    {
        this.message = message;
    }
    public static Message newEmergencyMessage(String message, String location)
    {
        Message m = new Message(message);
        m.location = location;
        return m;
    }
    public static Message newLogInMessage(String message, String userName, String password)
    {
        Message m = new Message(message);
        m.userName = userName;
        m.password = password;
        return m;
    }
    public static Message newLogInMessagetoClient(String message , int clientId ,Boolean isManager,String userName,UserDTO userDTO)
    {
        Message m = new Message(message);
        m.clientId=clientId;
        m.isManager=isManager;
        m.userName=userName;
         m.userDTO=userDTO;
       return m;
    }
    public static Message newCommunityListMessage( String message,int clientId)
    {
        Message m = new Message(message);
        m.clientId = clientId;
        return m;
    }
    public static Message newUserListMessage(String message,List<UserDTO> userDTOList)
    {
        Message m = new Message(message);
        m.userDTOList=userDTOList;
        return m;
    }
    public static  Message newEmergencyListMessage(String message ,List<EmergencyDisplay> emergencyDisplayList)
    {
       Message m = new Message(message);
       m.emergencyDisplayList=emergencyDisplayList;
       return m;
    }
    public static Message newEmergencyListToServerMessage(String message,LocalDateTime fromDateTime,LocalDateTime toDateTime,int clientId)
    {
       Message m = new Message(message);
       m.fromDateTime=fromDateTime;
       m.toDateTime=toDateTime;
       m.clientId=clientId;
       return m;
    }
    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }
    public int getClientId() {
        return clientId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<UserDTO> getUserDTOList()
    {
        return userDTOList;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getLocation()
    {
        return location;
    }

    public Boolean getIsManager()
    {
        return isManager;
    }

    public List<EmergencyDisplay> getEmergencyDisplayList() {
        return emergencyDisplayList;
    }


    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
