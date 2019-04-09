package cours.java.stic3.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Utilitaire 
{
	private static SessionFactory sessionFactory;
	 
	public static SessionFactory buildSessionFactory()
	{
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		ServiceRegistry service = new StandardServiceRegistryBuilder().
			applySettings(config.getProperties()).build();
		sessionFactory = config.buildSessionFactory(service);
		
		return sessionFactory;
		
		
	}
}

