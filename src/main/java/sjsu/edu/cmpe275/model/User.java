package sjsu.edu.cmpe275.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
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
	
	
	//constructor
	public User(long id, String email, String password, String accountType, String fullName, String screenName, String gender,
			String description, Address address, boolean isVerified) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.accountType = accountType;
		this.fullName = fullName;
		this.screenName = screenName;
		this.gender = gender;
		this.description = description;
		this.address = address;
		this.isVerified = isVerified;
	}

	public User() {
		
	}

	//getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", accountType=" + accountType + ", fullName=" + fullName
				+ ", screenName=" + screenName + ", gender=" + gender + ", description=" + description + ", address="
				+ address + "]";
	}
	
}
