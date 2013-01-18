package pl.styall.library.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractAddress implements CommonEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7682019935207959596L;

	public enum AddressType {
		HOME, CONTACT, OTHER
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @JsonView(Views.Admin.class)
	private Long id;

	@Size(min = 3, max = 20)
	@Column(nullable = false)
	@Pattern(regexp = "^[0-9a-zA-ZąćęłńóśżźĄĆĘŁŃÓŚŻŹ.-_]+$")
	private String city;

	@Size(min = 3, max = 20)
	@Column(nullable = false)
	@Pattern(regexp = "^[0-9a-zA-ZąćęłńóśżźĄĆĘŁŃÓŚŻŹ.-_]+$")
	private String street;

	@Pattern(regexp = "^[0-9]{2}-[0-9]{3}$")
	private String zip;

	@Size(min = 1, max = 20)
	@Column(nullable = false, name = "home_nr")
	@Pattern(regexp = "^[0-9a-zA-Z.-_]+$")
	private String homeNr;

	private String name;

	private AddressType type;

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHomeNr() {
		return homeNr;
	}

	public void setHomeNr(String homeNr) {
		this.homeNr = homeNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}
}
