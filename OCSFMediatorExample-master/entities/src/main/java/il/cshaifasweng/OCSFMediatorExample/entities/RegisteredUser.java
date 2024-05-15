package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
public class RegisteredUser extends User  {

    private String userName;
    private String passwordHash;
    private String passwordSalt;
    private boolean isManager;

//    @OneToMany(mappedBy = "registeredUser")
//    private List<Task> myTasksList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "registeredUser")
//    private List<Task> volunteeringRequestList  = new ArrayList<>();


    //constractor with manager
    public RegisteredUser(long idNum, String firstName, String lastName, String community, String location,Manager manager, String userName,String password) {
        super(idNum, firstName, lastName, community, location,manager);
        this.userName = userName;
        this.isManager = false;
        setPassword(password);
    }

    //constracture without manager
    public RegisteredUser(long idNum, String firstName, String lastName, String community, String location, String userName,String password) {
        super(idNum, firstName, lastName, community, location);
        this.userName = userName;
        this.isManager = false;
        setPassword(password);
    }

    public RegisteredUser() {}

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPassword(String password) {
        // Use a secure hash function (e.g., BCrypt) with a random salt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        this.passwordSalt = passwordEncoder.encode(password).substring(0, 29);
        this.passwordHash = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        // Use matches() to compare the provided password with the stored hash
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setISManager(boolean manager) {
        isManager = manager;
    }

}
