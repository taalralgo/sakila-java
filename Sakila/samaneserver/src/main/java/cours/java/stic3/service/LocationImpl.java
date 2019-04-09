package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Rental;

@SuppressWarnings("serial")
public class LocationImpl extends UnicastRemoteObject implements IRental
{

	public LocationImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rental addRental(Rental arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<Rental> getRentalByRentalDate(Date arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rental> getRentalByReturnDate(Date arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rental> getRentals() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Rental> category = session.createQuery("SELECT r FROM Rental r").list();
		return category;
	}

	@Override
	public Rental modifierRental(Rental arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

}
