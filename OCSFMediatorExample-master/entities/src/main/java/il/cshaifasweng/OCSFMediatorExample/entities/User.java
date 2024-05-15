package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User   {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int userId;
    private Long idNum;
    private String firstName;
    private String lastName;
    private String community;
    private String location;

    @ManyToOne
    @JoinColumn(name = "manager_id") // Specify the foreign key column name
    private   Manager manager;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Emergency> emergencyList = new ArrayList<>();

    public User(){}

    public User(long idNum, String firstName, String lastName, String community, String location,Manager manager) {
        this.idNum=idNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.community = community;
        this.location = location;
        this.manager=manager;
    }

    public User(long idNum, String firstName, String lastName, String community, String location) {
        this.idNum=idNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.community = community;
        this.location = location;
        this.manager=null;
    }

    public int getUserId() {
        return userId;
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getIdNum() {
        return idNum;
    }

    public void setIdNum(Long idNum) {
        this.idNum = idNum;
    }

    public void addToEmergencyList(Emergency emergency){
    this.emergencyList.add(emergency);
    }


    public List<Emergency> getEmetgencyList() {
        return this.emergencyList;
    }


}
