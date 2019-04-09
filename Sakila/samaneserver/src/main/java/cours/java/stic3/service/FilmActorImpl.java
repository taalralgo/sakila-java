package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmActor;

@SuppressWarnings("serial")
public class FilmActorImpl extends UnicastRemoteObject implements IFilmActor
{

	public FilmActorImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public FilmActor addFilmActor(FilmActor arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmActor> getFilmActor() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<FilmActor> filmActor = session.createQuery("SELECT f FROM FilmActor f").list();
		return filmActor;
	}

	@Override
	public FilmActor getFilmActorByActor(Actor arg0) throws RemoteException {
		return null;
	}

	@Override
	public FilmActor getFilmActorByFilm(Film arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmActor modifierFilmActor(FilmActor arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
