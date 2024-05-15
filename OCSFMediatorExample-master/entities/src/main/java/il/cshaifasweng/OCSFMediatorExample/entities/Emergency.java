package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Emergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emergencyId;
    private LocalDateTime emergencyDateTime;
    @Enumerated(EnumType.STRING)
    private EmergencyType emergencyType;
    @ManyToOne
    @JoinColumn(name = "user_userId")
    private User user;

    private String location;


    public Emergency() {}

    public Emergency(EmergencyType emergencyType, User user) {
        this.emergencyType = emergencyType;
        this.user = user;
        this.emergencyDateTime=LocalDateTime.now();
    }

    public Emergency(String location) {
        this.location = location;
        this.emergencyDateTime=LocalDateTime.now();

    }

    public LocalDateTime getEmergencyDateTime() {
        return emergencyDateTime;
    }



    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(EmergencyType emergencyType) {
        this.emergencyType = emergencyType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int emergencyId) {
        this.emergencyId = emergencyId;
    }

    public int getEmergencyId() {
        return emergencyId;
    }

}
