package sjsu.edu.cmpe275.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "event")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventID;
	
	private String title;
	private String description;
	private LocalDateTime startDate;
	private LocalDateTime endtDate;
	private LocalDateTime deadline;
	
	@Embedded
	private Address address;
	
	private int minParticpants;
	private int maxParticpants;
	private int fees;
	private String policy;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)  
    @JoinColumn(name = "userId")
//    @JsonIgnoreProperties({"players","address","teamId"})
    private User user;

	public Event(long eventID, String title, String description, LocalDateTime startDate, LocalDateTime endtDate, LocalDateTime deadline,
			Address address, int minParticpants, int maxParticpants, int fees, String policy, User user) {
		super();
		this.eventID = eventID;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endtDate = endtDate;
		this.deadline = deadline;
		this.address = address;
		this.minParticpants = minParticpants;
		this.maxParticpants = maxParticpants;
		this.fees = fees;
		this.policy = policy;
		this.user = user;
	}
	
	public Event() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return eventID;
	}

	public void setId(long eventID) {
		this.eventID = eventID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndtDate() {
		return endtDate;
	}

	public void setEndtDate(LocalDateTime endtDate) {
		this.endtDate = endtDate;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getMinParticpants() {
		return minParticpants;
	}

	public void setMinParticpants(int minParticpants) {
		this.minParticpants = minParticpants;
	}

	public int getMaxParticpants() {
		return maxParticpants;
	}

	public void setMaxParticpants(int maxParticpants) {
		this.maxParticpants = maxParticpants;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
