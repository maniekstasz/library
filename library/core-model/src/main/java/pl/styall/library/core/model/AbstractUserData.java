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






@MappedSuperclass
public abstract class AbstractUserData<ADDRESS extends AbstractAddress> implements CommonEntity{
	
	private static final long serialVersionUID = 4042781705716825393L;
	public enum Sex {MALE, FEMALE};
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min=3,max=20)
	@Pattern(regexp="^[a-zA-ZÄ…Ä‡Ä™Å‚Å„Ã³Å›Å¼ÅºÄ„Ä†Ä˜ÅÅƒÃ“ÅšÅ»Å¹., -]+$")
	@Column(nullable=false)
	private String name;
	
	@Pattern(regexp="^[a-zA-ZÄ…Ä‡Ä™Å‚Å„Ã³Å›Å¼ÅºÄ„Ä†Ä˜ÅÅƒÃ“ÅšÅ»Å¹., -]+$")
	@Size(min=3,max=20)
	@Column(nullable=false)
	private String surname;
	
	
	@NotNull
	@Enumerated
	@Column(nullable=false)
	private Sex sex;
	
	private String phone;
	
	@NotNull
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern="YYYY-MM-DD")
	private Date birthDate;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ADDRESS> addresses = new ArrayList<ADDRESS>();
	
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
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
