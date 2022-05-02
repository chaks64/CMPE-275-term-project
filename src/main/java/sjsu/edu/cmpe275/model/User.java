package sjsu.edu.cmpe275.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	//Columns
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String email;
	
	private String accountType;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(unique = true)
	private String screenName;
	
	private String gender;
	private String description;
	private String address;
	private boolean isVerified;
	
	
	//constructor
	public User(long id, String email, String accountType, String fullName, String screenName, String gender,
			String description, String address, boolean isVerified) {
		super();
		this.id = id;
		this.email = email;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", accountType=" + accountType + ", fullName=" + fullName
				+ ", screenName=" + screenName + ", gender=" + gender + ", description=" + description + ", address="
				+ address + "]";
	}
	
}
