package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;

import cours.java.stic3.model.Actor;

public class ActorImpl extends UnicastRemoteObject implements IActor
{

	public ActorImpl() throws RemoteException {
		super();
	}
	
	@Override
	public List<Actor> getActors() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Actor> actors = session.createQuery("SELECT a FROM Actor a").list();
		return actors;
	}

	@Override
	public List<Actor> getActorByFirstName(String firstName) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		Query hql = session.createQuery("SELECT a FROM Actor a WHERE a.firstName LIKE :name");
		hql.setParameter("name", "%"+firstName+"%");
		List<Actor> actors = hql.list();
		return actors;
	}

	@Override
	public Actor getActorById(int arg0) throws RemoteException {
		return null;
		
	}

	@Override
	public List<Actor> getActorByLastName(String lastName) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		Query hql = session.createQuery("SELECT a FROM Actor a WHERE a.lastName LIKE :name");
		hql.setParameter("name", "%"+lastName+"%");
		List<Actor> actors = hql.list();
		return actors;
	}

	@Override
	public Actor addActor(Actor arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public Actor modifierActor(Actor arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	

}
