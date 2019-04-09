package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import cours.java.stic3.model.Film;

public interface IFilm extends Remote 
{
	//Ajout et modification
	public Film addFilm(Film film) throws RemoteException;
	public Film modifierFilm(Film film) throws RemoteException;
	//Recuperation
	public Film getFilmById(int filmId) throws RemoteException;
	public Film getFilmByTitle(String title) throws RemoteException;
	public List<Film> getFilms() throws RemoteException;
	public List<Film> getFilmsByLangue(String langue) throws RemoteException;
	public List<Film> getFilmsByTitre(String title) throws RemoteException;
	public List<Film> getFilmByReleaseYear(Date date) throws RemoteException;
}
