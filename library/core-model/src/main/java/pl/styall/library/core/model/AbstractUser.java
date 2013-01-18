package pl.styall.library.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

@MappedSuperclass
public abstract class AbstractUser<USER_DATA extends AbstractUserData<ADDRESS>, ADDRESS extends AbstractAddress>  implements CommonEntity{
	
	private static final long serialVersionUID = -8891110973911665190L;
	public enum Type {USER, BRAND}
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	
	@Valid
	@Embedded
	private Credentials credentials;
	
	//TODO: change to lazy fetching but seriously lazy fetchings
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private USER_DATA userData;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date dateRegistered;
	
	public Long getId(){
		return id;
	}
	private void setId(Long id){
		this.id = id;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public USER_DATA getUserData() {
		return userData;
	}
	public void setUserData(USER_DATA userData) {
		this.userData = userData;
	}
	public Date getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	public void addUserRole(UserRole role) {
		userRoles.add(role);
	}
	
}
