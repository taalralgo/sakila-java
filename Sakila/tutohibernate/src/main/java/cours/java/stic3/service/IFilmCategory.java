package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Category;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmCategory;

public interface IFilmCategory extends Remote 
{
	//Modification
	public FilmCategory addFilmCategory(FilmCategory filmActeur) throws RemoteException;
	public FilmCategory modifierFilmCategory(FilmCategory filmCategoryId) throws RemoteException;
	//Recuperation
	public List<FilmCategory> getFilmsCategories() throws RemoteException;
	public List<FilmCategory> getFilmsCategoriesByFilm(Film film) throws RemoteException;
	public List<FilmCategory> getFilmsCategoriesByCategory(Category category) throws RemoteException;
}
