package pl.styall.library.core.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


@MappedSuperclass
public abstract class AbstractCompany<ADDRESS extends AbstractAddress> extends CommonEntity {

	private static final long serialVersionUID = 3133209022576222042L;


	@NotEmpty
	// @JsonView(Views.Owner.class)
	private String name;

	@NotEmpty
	@Pattern(regexp = "^[0-9+]+$")
	private String phone;

	// @JsonView(Views.Owner.class)

	@NotEmpty
	@Pattern(regexp = "^[0-9]{10}$")
	private String nip;



	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date dateRegistered;

	@Valid
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private ADDRESS address;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	private void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ADDRESS getAddress() {
		return address;
	}

	public void setAddress(ADDRESS address) {
		this.address = address;
	}

}
