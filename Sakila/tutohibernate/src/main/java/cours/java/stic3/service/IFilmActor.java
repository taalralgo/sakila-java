package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmActor;

public interface IFilmActor extends Remote 
{
	//Modification
	public FilmActor addFilmActor(FilmActor filmActeur) throws RemoteException;
	public FilmActor modifierFilmActor(FilmActor filmActor) throws RemoteException;
	//Recuperation
	public FilmActor getFilmActorByFilm(Film film) throws RemoteException;
	public List<FilmActor> getFilmActor() throws RemoteException;
	public FilmActor getFilmActorByActor(Actor actor) throws RemoteException;
}
