package sjsu.edu.cmpe275.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    
    public Address() {
    	
	}

	public Address(String street, String number, String city, String state, String zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
    
}
