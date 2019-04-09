package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Category;

@SuppressWarnings("serial")
public class CategoryImpl extends UnicastRemoteObject implements ICategory 
{

	public CategoryImpl() throws RemoteException {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Category> category = session.createQuery("SELECT c FROM Category c").list();
		return category;
	}

	@Override
	public Category addCategory(Category arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public Category getCategoryById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategoryByName(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category modifier(Category arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

}
