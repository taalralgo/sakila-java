package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Film;

@SuppressWarnings("serial")
public class FilmImpl extends UnicastRemoteObject implements IFilm 
{

	public FilmImpl() throws RemoteException {
		super();
	}
	
	@Override
	public Film addFilm(Film arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}
	
	@Override
	public Film modifierFilm(Film arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getFilms() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		List<Film> films = session.createQuery("SELECT f FROM Film f").list();
		return films;
	}
	
	@Override
	public List<Film> getFilmsByTitre(String arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		Query hql = session.createQuery("SELECT f FROM Film f WHERE f.title LIKE :name");
		hql.setParameter("name", "%"+arg0+"%");
		List<Film> films = hql.list();
		return films;
	}
	
	@Override
	public Film getFilmById(int arg0) throws RemoteException {
		return null;
	}

	@Override
	public List<Film> getFilmByReleaseYear(Date arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film getFilmByTitle(String arg0) throws RemoteException {
		return null;
		
	}

	@Override
	public List<Film> getFilmsByLangue(String arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		Query hql = session.createQuery("SELECT f FROM Film f WHERE f.languageId.name LIKE :name");
		hql.setParameter("name", "%"+arg0+"%");
		List<Film> films = hql.list();
		return films;
	}

	

	

}
