package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Actor;

public interface IActor extends Remote 
{
	//Ajout et modification
	public Actor addActor(Actor acteur) throws RemoteException;
	public Actor modifierActor(Actor actor) throws RemoteException;
	//Recuperations
	public List<Actor> getActors() throws RemoteException;
	public Actor getActorById(int actorId) throws RemoteException;
	public List<Actor> getActorByFirstName(String firstName) throws RemoteException;
	public List<Actor> getActorByLastName(String lastName) throws RemoteException;
	
	
}
