package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Category;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmCategory;

public class FilmCategoryImpl extends UnicastRemoteObject implements IFilmCategory {

	public FilmCategoryImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public FilmCategory addFilmCategory(FilmCategory arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<FilmCategory> getFilmsCategories() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<FilmCategory> filmCatego = session.createQuery("SELECT f FROM FilmActor f").list();
		return filmCatego;
	}

	@Override
	public List<FilmCategory> getFilmsCategoriesByCategory(Category arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmCategory> getFilmsCategoriesByFilm(Film arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmCategory modifierFilmCategory(FilmCategory arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
