package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Address;
import cours.java.stic3.model.Category;

public class AdressImpl extends UnicastRemoteObject implements IAddress 
{

	public AdressImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Address ModifierAddress(Address arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address addAdresse(Address arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<Address> getAddress() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Address> addresses = session.createQuery("SELECT a FROM Address a").list();
		return addresses;
	}

	@Override
	public List<Address> getAddressByDistrict(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getAddressById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> getAddressByNom(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
