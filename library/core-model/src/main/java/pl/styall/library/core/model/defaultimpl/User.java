package pl.styall.library.core.model.defaultimpl;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="users",uniqueConstraints={@UniqueConstraint(columnNames={"username"}), @UniqueConstraint(columnNames={"mail"})})
public class User extends pl.styall.library.core.model.AbstractUser<UserData, Address> {

	private static final long serialVersionUID = 134892933032756168L;

}
