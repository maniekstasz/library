package pl.styall.library.core.form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import pl.styall.library.core.model.AbstractAddress;
import pl.styall.library.core.model.AbstractUserData;



public class UserRegForm<USER_DATA extends AbstractUserData, ADDRESS extends AbstractAddress> extends UserFormModel {
	
	private static final long serialVersionUID = -478416500302410517L;

	@Valid
	@NotNull
	private USER_DATA userData;
	
	@Valid
	private ADDRESS address;

	public ADDRESS getAddress() {
		return address;
	}
	public void setAddress(ADDRESS address) {
		this.address = address;
	}
	public USER_DATA getUserData() {
		return userData;
	}
	public void setUserData(USER_DATA userData) {
		this.userData = userData;
	}
}
