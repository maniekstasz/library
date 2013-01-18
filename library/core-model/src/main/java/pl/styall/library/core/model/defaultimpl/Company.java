package pl.styall.library.core.model.defaultimpl;

import javax.persistence.Entity;
import javax.persistence.Table;

import pl.styall.library.core.model.AbstractCompany;

@Entity
@Table(name="comapny")
public class Company extends AbstractCompany<Address>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1388158069682687204L;

}
