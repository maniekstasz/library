package pl.styall.library.spring;

import org.hibernate.Session;

public interface DatabasePopulator {

	void populate(Session currentSession);
}
