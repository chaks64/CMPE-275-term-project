package sjsu.edu.cmpe275.model;

//import org.codehaus.jackson.annotate.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="forum")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long msgID;

    private String msg;
    private String img;


    private String forumType;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "user1",
            joinColumns = @JoinColumn(name = "msgID", referencedColumnName = "msgID"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "userID"))
//    @JsonIgnoreProperties({"address","team","opponents"})
    private Set<User> user = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "event1",
            joinColumns = @JoinColumn(name = "msgID", referencedColumnName = "msgID"),
            inverseJoinColumns = @JoinColumn(name = "eventID", referencedColumnName = "eventID"))
//    @JsonIgnoreProperties({"address","team","opponents"})
    private Set<Event> event = new HashSet<>();

    public Forum(long msgID, String msg,String img, String forumType, Set<User> user, Set<Event> event) {
        super();
        this.msgID = msgID;
        this.msg = msg;
        this.img = img;
        this.forumType = forumType;
        this.user = user;
        this.event = event;
    }

    public Forum(){
        // TODO Auto-generated constructor stub
    }

    public long getmsgID() {
        return msgID;
    }

    public void setmsgID(long msgID) {
        this.msgID = msgID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getForumType() {
        return forumType;
    }

    public void setForumType(String forumType) {
        this.forumType = forumType;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Set<Event> getEvent() {
        return event;
    }

    public void setEvent(Set<Event> event) {
        this.event = (Set<Event>) event;
    }

}
