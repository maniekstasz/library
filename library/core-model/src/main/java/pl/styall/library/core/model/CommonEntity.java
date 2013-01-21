package pl.styall.library.core.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@MappedSuperclass
public class CommonEntity implements Serializable {

	private static final long serialVersionUID = -8621541507119130733L;

	@Id
	@Column(length=24)
//	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "base64-uuid")
	@GenericGenerator(name = "base64-uuid", strategy = "pl.styall.library.core.ext.Base64UUIDGenerator",parameters = { @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
