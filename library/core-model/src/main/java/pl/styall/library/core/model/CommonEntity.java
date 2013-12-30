package pl.styall.library.core.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@MappedSuperclass
public class CommonEntity implements Serializable {

	private static final long serialVersionUID = -8621541507119130733L;

	@Id
	// @Column(length=24)
	// @Type(type = "pg-uuid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GenericGenerator(name = "base64-uuid", strategy =
	// "pl.styall.library.core.ext.Base64UUIDGenerator",parameters = {
	// @Parameter(name = "uuid_gen_strategy_class", value =
	// "org.hibernate.id.uuid.CustomVersionOneStrategy") })

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof CommonEntity))
			return false;

		final CommonEntity entity = (CommonEntity) other;

		if (!entity.getId().equals(getId()))
			return false;

		return true;
	}
	

}
