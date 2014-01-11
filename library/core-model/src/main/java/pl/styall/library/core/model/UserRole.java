package pl.styall.library.core.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -4235260960346350992L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, length=64)
	private String role;
	
	
	@Override
	public boolean equals(Object obj) {
		return role.equals(((UserRole) obj).getRole());
	}
		
	public UserRole() {
		this.setRole("ROLE_ANONYMOUS");
	}

	public UserRole(String role) {
		this.setRole(role);
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
