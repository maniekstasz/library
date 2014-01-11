package pl.styall.library.spring;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseInitPopulator implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private SessionFactory sessionFactory;

	private List<DatabasePopulator> populators = new ArrayList<DatabasePopulator>();

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getId().contains("Dispatcher")) {
			for (DatabasePopulator populator : populators) {
				populator.populate(sessionFactory.getCurrentSession());
			}
		}
	}

	public void adPopulator(DatabasePopulator populator) {
		populators.add(populator);
	}
}
