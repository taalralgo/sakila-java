package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Category;
import cours.java.stic3.model.Store;

public class StoreImpl extends UnicastRemoteObject implements IStore
{

	public StoreImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Store addStore(Store arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public Store getStoreById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Store> getStores() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Store> store = session.createQuery("SELECT s FROM Store s").list();
		return store;
	}

	@Override
	public Store modifierStore(Store arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

}
