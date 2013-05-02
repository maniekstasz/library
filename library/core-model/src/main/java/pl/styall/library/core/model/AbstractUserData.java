package pl.styall.library.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;




@MappedSuperclass
public abstract class AbstractUserData<ADDRESS extends AbstractAddress> extends CommonEntity{
	
	private static final long serialVersionUID = 4042781705716825393L;
	public enum Sex {MALE, FEMALE};
	
	

	private String name;
	

	private String surname;
	
	
	@Enumerated
	private Sex sex;
	
	private String phone;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ADDRESS> addresses = new ArrayList<ADDRESS>();
	
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public List<ADDRESS> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<ADDRESS> addresses) {
		this.addresses = addresses;
	}
	public void addAddress(ADDRESS address) {
		this.addresses.add(address);
	}
}
