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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

@MappedSuperclass
public abstract class AbstractUser<USER_DATA extends AbstractUserData, ADDRESS extends AbstractAddress>
		extends CommonEntity {

	private static final long serialVersionUID = -8891110973911665190L;

	public enum Type {
		USER, BRAND
	}

	@Embedded
	private Credentials credentials;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "user_data_id")
	private USER_DATA userData;

	@JoinTable(name = "user_user_role_maps", inverseJoinColumns = { @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false) }, joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) })
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserRole> userRoles;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", name="creation_date")
	private Date creationDate;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name = "user_address_maps", inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") }, joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private List<ADDRESS> addresses ;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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


	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void addUserRole(UserRole role) {
		if (userRoles == null) {
			userRoles = new ArrayList<UserRole>();
		}
		userRoles.add(role);
	}

	public List<ADDRESS> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<ADDRESS> addresses) {
		this.addresses = addresses;
	}
	public void addAddress(ADDRESS address) {
		if(addresses == null){
			addresses = new ArrayList<ADDRESS>();
		}
		addresses.add(address);
	}

}
