package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.EmergencyDisplay;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.UserDTO;

import java.io.Serializable;
import java.util.List;

public class MessageEvent implements Serializable {
    private String message;

    private List<UserDTO> communityMemList;
    private List<EmergencyDisplay> emergencyDisplayList;

    MessageEvent(String message)
{
    this.message= message;
}

    public static MessageEvent newEmergencyListMessage(String message, List<EmergencyDisplay> emergencyDisplayList) {
         MessageEvent m = new MessageEvent(message);
        m.emergencyDisplayList=emergencyDisplayList;
          return m;
    }

    public static MessageEvent newUserListMessage(String message, List<UserDTO> communityMemList) {
        MessageEvent m = new MessageEvent(message);
        m.communityMemList=communityMemList;
        return m;
    }

    public String getMessage() {
        return message;
    }

    public List<EmergencyDisplay> getEmergencyDisplayList() {
        return emergencyDisplayList;
    }

    public List<UserDTO> getCommunityMemList() {
        return communityMemList;
    }
}