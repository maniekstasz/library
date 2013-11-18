package pl.styall.library.core.security.rest;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user_auth_token")
public class UserAuthToken {
	
	private  String login;
	
	@Id
    private  String series;
	
	@Column(name="token")
    private  String tokenValue;
    @Column(name="token_creation_date")
    private Date date;

    public UserAuthToken(){
    	
    }
    
    public UserAuthToken(String login, String series, String tokenValue, Date date) {
        this.login = login;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public String getSeries() {
        return series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getDate() {
        return date;
    }

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void clearTokenValue(){
		this.tokenValue = null;
	}
}
