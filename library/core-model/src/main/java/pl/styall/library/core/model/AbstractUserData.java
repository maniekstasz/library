package pl.styall.library.core.model;

import java.util.Date;
import javax.persistence.*;


import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;




@MappedSuperclass
public abstract class AbstractUserData extends CommonEntity{
	
	private static final long serialVersionUID = 4042781705716825393L;
	public enum Sex {MALE, FEMALE};
	
	private String name;
	
	private String surname;
	
	@Enumerated
	private Sex sex;
	
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth_date",nullable=false)
	private Date birthDate;
	
	@Column(name="image_url")
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
	
}
