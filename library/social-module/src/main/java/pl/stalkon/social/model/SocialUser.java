package pl.stalkon.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.springframework.social.connect.jpa.RemoteUser;



@Entity
@Table(name = "SocialUser")
@org.hibernate.annotations.Table(appliesTo = "SocialUser", indexes = {
		@org.hibernate.annotations.Index(name = "idxSocialProviderUser", columnNames = {
				"userId", "providerId", "irank", "providerUserId" }),
		@org.hibernate.annotations.Index(name = "idxSocialProvider", columnNames = {
				"providerId", "providerUserId" }) })
public class SocialUser implements RemoteUser {
	private Long id, expireTime;
	private String providerUserId, providerId, secret, displayName, profileUrl,
			imageUrl, accessToken, refreshToken;
	private int rank;

	@NotNull
	private String userId;
	
	public SocialUser() {
	}

	public SocialUser(String userId, String providerId, String providerUserId,
			int rank, String displayName, String profileUrl, String imageUrl,
			String accessToken, String secret, String refreshToken,
			Long expireTime) {
		super();
		this.expireTime = expireTime;
		this.providerUserId = providerUserId;
		this.providerId = providerId;
		this.secret = secret;
		this.displayName = displayName;
		this.profileUrl = profileUrl;
		this.imageUrl = imageUrl;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.rank = rank;
		this.userId = userId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "socialUserId", nullable = true, insertable = false, updatable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 25, nullable = false)
	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String provider) {
		this.providerUserId = provider;
	}


	@Column(length = 25, nullable = false)
	@Index(name = "idxSocialProviderId")
	public String getProviderId() {
		return providerId;
	}


	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Column(name = "irank")
	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}

	@Column(length = 55)
	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}


	@Column(length = 55)
	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	@Column
	public String getProfileUrl() {
		return profileUrl;
	}


	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}


	@Column
	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@Column(length = 120)
	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		System.out.println(accessToken.length());
		this.accessToken = accessToken;
	}


	@Column(length = 70)
	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	@Column
	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((providerUserId == null) ? 0 : providerUserId.hashCode());
		result = prime * result
				+ ((providerId == null) ? 0 : providerId.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass()))
			return false;
		SocialUser other = (SocialUser) obj;
		if (providerUserId == null) {
			if (other.providerUserId != null)
				return false;
		} else if (!providerUserId.equals(other.providerUserId))
			return false;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
			return false;
		return true;
	}

}
