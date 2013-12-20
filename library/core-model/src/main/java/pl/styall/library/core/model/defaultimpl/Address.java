package pl.styall.library.core.model.defaultimpl;

import javax.persistence.Entity;
import javax.persistence.Table;

import pl.styall.library.core.model.AbstractAddress;

@Entity
@Table(name="addresses")
public class Address extends AbstractAddress{

	private static final long serialVersionUID = -2660870180276931483L;

}
