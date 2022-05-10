package sjsu.edu.cmpe275.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String accountType;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(unique = true)
	private String screenName;
	
	private String gender;
	private String description;
	
	@Embedded
	private Address address;
	
	private boolean isVerified;
	
	private String googleSubId;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
//    @JsonIgnoreProperties({"team","address","opponents"})
    private Set<Event> event = new HashSet<>();
	
	
	//constructor
	public User(long userId, String email, String password, String accountType, String fullName, String screenName, String gender,
			String description, Address address, boolean isVerified , Set<Event> event) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.accountType = accountType;
		this.fullName = fullName;
		this.screenName = screenName;
		this.gender = gender;
		this.description = description;
		this.address = address;
		this.isVerified = isVerified;
		this.event = event;
	}

	public User() {
		
	}

	//getters and setters
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getGoogleSubId() {
		return googleSubId;
	}

	public void setGoogleSubId(String googleSubId) {
		this.googleSubId = googleSubId;
	}
	
	public Set<Event> getEvent() {
		return event;
	}

	public void setEvent(Set<Event> event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", email=" + email + ", accountType=" + accountType + ", fullName=" + fullName
				+ ", screenName=" + screenName + ", gender=" + gender + ", description=" + description + ", address="
				+ address + "]";
	}
	
}
