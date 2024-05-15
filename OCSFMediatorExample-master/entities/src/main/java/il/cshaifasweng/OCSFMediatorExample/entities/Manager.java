package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public  class Manager extends RegisteredUser  {

    // Override the userId field inherited from User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AttributeOverride(name = "userId", column = @Column(name = "manager_id")) // Customize the column name for Manager's primary key
    private int userId; // This will map to the "manager_id" column in the database

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<User> communityMemList = new ArrayList<>();

    public Manager() {}

    public Manager(long idNum, String firstName, String lastName, String community, String location, String userName, String password) {
        super(idNum, firstName, lastName, community, location, userName, password);
         this.setISManager(true);
    }

    public List<User> getCommunityMemList() {
        return communityMemList;
    }

    public void addToCommunityMemList(User user) {

        this.communityMemList.add(user);
    }

}
