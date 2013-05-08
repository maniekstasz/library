package pl.styall.library.core.security.filter;

import java.io.Serializable;

public class UserMessageSessionAttribute implements Serializable {

	private static final long serialVersionUID = -405904888084923217L;

	private long timestamp;
	private int unhandledMessagesCount;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getUnhandledMessagesCount() {
		return unhandledMessagesCount;
	}

	public void setUnhandledMessagesCount(int unhandledMessagesCount) {
		this.unhandledMessagesCount = unhandledMessagesCount;
	}

}
