package il.cshaifasweng.OCSFMediatorExample.entities;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private int userId;
    private long idNum;
    private String  firstName;
    private String lastName;
    private String community;
    private String location;
    private String managerName;

    public UserDTO() {}

    //  constructor to convert a User entity to UserDTO
    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.idNum=user.getIdNum();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.community = user.getCommunity();
        this.location = user.getLocation();
        if(user.getManager()!=null)
        this.managerName = user.getManager().getFirstName() +" "+ user.getManager().getLastName();
        else managerName = "You are the manager";
    }

    public long getIdNum() {
        return idNum;
    }

    public void setIdNum(long idNum) {
        this.idNum = idNum;
    }

    public String getIdNumtoString() {
        return "" + idNum ;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getFirstName() {
        return firstName;
    }
}

