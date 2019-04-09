package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import cours.java.stic3.model.Rental;

public interface IRental extends Remote 
{
	//Modification
	public Rental addRental(Rental rental) throws RemoteException;
	public Rental modifierRental(Rental rental) throws RemoteException;
	//Recuperation
	public List<Rental> getRentals() throws RemoteException;
	public List<Rental> getRentalByRentalDate(Date date) throws RemoteException;
	public List<Rental> getRentalByReturnDate(Date date) throws RemoteException;
}
