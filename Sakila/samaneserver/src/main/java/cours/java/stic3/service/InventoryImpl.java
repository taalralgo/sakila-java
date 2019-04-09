package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Inventory;

@SuppressWarnings("serial")
public class InventoryImpl extends UnicastRemoteObject implements IIventory
{

	public InventoryImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Inventory addInventory(Inventory arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventory> getInventories() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Inventory> category = session.createQuery("SELECT i FROM Inventory i").list();
		return category;
	}

	@Override
	public List<Inventory> getInventoryById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inventory> getInventoryByStore(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory modifierInventory(Inventory arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}
	
}