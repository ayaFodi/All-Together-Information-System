package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

public class EmergencyDisplay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int emergencyId;
    private  Long idNum;

    private int userId;

    private  String firstName;

    private  String lastName;

    private  String community;

    private  String location;

    private String type;

    private  LocalDateTime emergencyDateTime;


    public EmergencyDisplay() {}

    public EmergencyDisplay(User user, Emergency emergency)
    {
        this.idNum = user.getIdNum();
        this.userId= user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.community = user.getCommunity();
        this.location = user.getLocation();
        this.type=emergency.getEmergencyType().toString();
        this.emergencyDateTime = emergency.getEmergencyDateTime();
    }

    public Long getIdNum() {
        return idNum;
    }

    public void setIdNum(Long idNum) {
        this.idNum = idNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(int emergencyId) {
        this.emergencyId = emergencyId;
    }

    public LocalDateTime getEmergencyDateTime() {
        return emergencyDateTime;
    }

    public void setEmergencyDateTime(LocalDateTime emergencyDateTime) {
        this.emergencyDateTime = emergencyDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
